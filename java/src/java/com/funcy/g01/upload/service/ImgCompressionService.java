package com.funcy.g01.upload.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.funcy.g01.base.bo.role.Role;
import com.funcy.g01.base.dao.redis.RoleRepo;
import com.funcy.g01.base.exception.ErrorCode;
import com.funcy.g01.base.global.ServerConfig;
import com.funcy.g01.base.platform.aliyun.OssHelper;
import com.funcy.g01.base.proto.service.ReqCmdProto.UploadImgReqProto;
import com.funcy.g01.base.util.JSONUtils;
import com.funcy.g01.hall.service.AccountService;


@SuppressWarnings("serial")
public class ImgCompressionService extends HttpServlet{
	
	private OssHelper ossHelper;
	
	private RoleRepo roleRepo;
	
	public void init(ServletConfig config) throws ServletException { 
        super.init(config); 
        ServletContext servletContext = this.getServletContext();
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        this.ossHelper = wac.getBean(OssHelper.class);
        this.roleRepo = wac.getBean(RoleRepo.class);
	} 
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		uploadImgToOss(request,response);
	}
	
	/**
	 * 上传图片到Oss
	 * @param request
	 * @param response
	 */
	public void uploadImgToOss(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ServletInputStream in = request.getInputStream();
		UploadImgReqProto uploadImgReqProto = UploadImgReqProto.parseFrom(in);
		Role role = roleRepo.getRole(uploadImgReqProto.getRoleId());
		//返回数据准备
		Map<String, Object> respMap = new HashMap<String,Object>(); 
        //图片处理
		try{
			//token验证
			checkToken(role.getPlatformId(),uploadImgReqProto.getToken());
			//压缩成小图
		    byte[] smallImgBytes = compressImg(uploadImgReqProto.getImageData().toByteArray(), 130, 130, true);
		    
		    //小图上传OSS
		    Future<?> future1 = this.ossHelper.uploadImageByBytes(smallImgBytes, uploadImgReqProto.getPngName());
		    //大图上传OSS
		    this.ossHelper.uploadBigImageByBytes(uploadImgReqProto.getImageData().toByteArray(), uploadImgReqProto.getPngName());
		   
		    future1.get();
        }catch (Exception e) {
            e.printStackTrace();
		}
		respMap.put("result", 0);//表成功
		response.resetBuffer();
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().print(JSONUtils.toJSON(respMap));
	}
	
	private void checkToken(String platformId, String token){
		String[] temp = token.split(";");
		long tokenCreateTime = Long.parseLong(temp[0]);
		String actToken = temp[1];
		String serverToken = DigestUtils.md5Hex(platformId + tokenCreateTime + ServerConfig.login_token_ext);
		if (!serverToken.equals(actToken)) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * 传入图片byte[],传出压缩后的图片byte[]
	 * 
	 * @param imageByte
	 *            图片字节数组
	 * @param width
	 *            生成小图片宽度
	 * @param height
	 *            生成小图片高度
	 * @param gp
	 *            是否等比缩放
	 * @return
	 */
	public static byte[] compressImg(byte[] imageByte, int width, int height, boolean gp) {
		byte[] inByte = null;
		try {
			ByteArrayInputStream byteInput = new ByteArrayInputStream(imageByte);
			Image img = ImageIO.read(byteInput);
			// 判断图片格式是否正确
			if (img.getWidth(null) == -1) {
				return inByte;
			} else {
				int newWidth;
				int newHeight;
				// 判断是否是等比缩放
				if (gp == true) {
					// 为等比缩放计算输出的图片宽度及高度
					double rate1 = ((double) img.getWidth(null)) / (double) width + 0.1;
					double rate2 = ((double) img.getHeight(null)) / (double) height + 0.1;
					// 根据缩放比率大的进行缩放控制
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					newWidth = width; // 输出的图片宽度
					newHeight = height; // 输出的图片高度
				}
				BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
				img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
				/*
				 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
				 */
				tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);

				ImageWriter imgWrier;
				ImageWriteParam imgWriteParams;
				
				// 指定写图片的方式为 png
				imgWrier = ImageIO.getImageWritersByFormatName("png").next();
				imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
				// 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
				imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
				// 这里指定压缩的程度，参数qality是取值0~1范围内，
				imgWriteParams.setCompressionQuality(0.7f);

				imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
				ColorModel colorModel = ColorModel.getRGBdefault();
				
				// 指定压缩时使用的色彩模式
				imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,
						colorModel.createCompatibleSampleModel(100, 100)));

				// 将压缩后的图片返回字节流
				ByteArrayOutputStream out = new ByteArrayOutputStream(imageByte.length);
				imgWrier.reset();
				// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
				// OutputStream构造
				imgWrier.setOutput(ImageIO.createImageOutputStream(out));
				// 调用write方法，就可以向输入流写图片
				imgWrier.write(null, new IIOImage(tag, null, null), imgWriteParams);
				out.flush();
				out.close();
				byteInput.close();
				inByte = out.toByteArray();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return inByte;
	}
	
}
