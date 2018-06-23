package com.funcy.g01.base.platform.aliyun;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Future;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.oss.OSSClient;
import com.funcy.g01.base.global.PlatformConfig;

@Component
public class OssHelper {

	@Autowired
	private PlatformConfig platformConfig;
	
	@Autowired
	private OssThreadPool ossThreadPool;
	
	private OSSClient ossClient;
	
	public void init() {
		this.ossClient = new OSSClient(platformConfig.getAliyunOssUrl(), platformConfig.getAliyunOssAccessKeyId(), platformConfig.getAliyunOssSecretAccessKey());
		
	}
	/**
	 * 通过byte数组的小图上传（游戏头像）
	 * @param bytes
	 * @param pngName
	 * @return
	 */
	public Future<?> uploadImageByBytes(final byte[] bytes, final String pngName) {
		return ossThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				uploadImageByBytes0(bytes, pngName, platformConfig.getAliyunOssNomalImgPath());
			}
		});
	}
	
	/**
	 * 通过byte数组的大图上传（游戏头像）
	 * @param bytes
	 * @param pngName
	 */
	public Future<?> uploadBigImageByBytes(final byte[] bytes, final String pngName) {
		return ossThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				uploadImageByBytes0(bytes, pngName, platformConfig.getAliyunOssBigImgPath());
			}
		});
	}
	
	/**
	 * 通过byte数组上传到OSS的基本方法
	 * @param bytes
	 * @param pngName
	 * @param path
	 */
	private void uploadImageByBytes0(byte[] bytes, String pngName, String path) {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ossClient.putObject(platformConfig.getAliyunOssBucket(), path + pngName + ".png", bais);
	}
	
	/**
	 * 通过图片url的小图上传到OSS
	 * @param urlStr
	 * @param pngName
	 * @param path
	 */
	
	private void uploadImage0(String urlStr, String pngName, String path) {
		System.out.println(urlStr);
//		OSSClient ossClient = new OSSClient(platformConfig.getAliyunOssUrl(), platformConfig.getAliyunOssAccessKeyId(), platformConfig.getAliyunOssSecretAccessKey());
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(300);
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
            urlConnection.setReadTimeout(300);
            urlConnection.setDoOutput(false);
            urlConnection.connect();
			InputStream in = urlConnection.getInputStream();
			BufferedImage old = ImageIO.read(in);
			BufferedImage newImage = new BufferedImage(99, 99, BufferedImage.TYPE_4BYTE_ABGR);
			newImage.getGraphics().drawImage(old, 0, 0, 99, 99, null);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(newImage, "png", out);
			ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());
			ossClient.putObject(platformConfig.getAliyunOssBucket(), path + pngName + ".png", bis);
			in.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
	}
	
	/**
	 * 通过图片url的大图上传到OSS
	 * @param urlStr
	 * @param pngName
	 * @param path
	 */
	private void uploadImage1(String urlStr, String pngName, String path) {
		System.out.println(urlStr);
//		OSSClient ossClient = new OSSClient(platformConfig.getAliyunOssUrl(), platformConfig.getAliyunOssAccessKeyId(), platformConfig.getAliyunOssSecretAccessKey());
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(300);
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
            urlConnection.setReadTimeout(300);
            urlConnection.setDoOutput(false);
            urlConnection.connect();
			InputStream in = urlConnection.getInputStream();
			BufferedImage old = ImageIO.read(in);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(old, "png", out);
			ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());
			ossClient.putObject(platformConfig.getAliyunOssBucket(), path + pngName + ".png", bis);
			in.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
	}
	
	
	/**
	 * 通过图片url上传
	 * @param urlStr
	 * @param pngName
	 */
	public void uploadImage(final String urlStr, final String pngName) {
		//小图
		uploadImage0(urlStr, pngName, platformConfig.getAliyunOssNomalImgPath());
		ossThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				//大图
				uploadImage1(urlStr, pngName, platformConfig.getAliyunOssBigImgPath());
			}
		});
	}
	
	public static void main(String[] args) {
		String urlStr = "http://q.qlogo.cn/qqapp/1106164446/08DDD25E3F66154EF92C899B35DBFEA3/40";
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(urlStr);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(300);
            urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
            urlConnection.setReadTimeout(300);
            urlConnection.setDoOutput(false);
            urlConnection.connect();
			InputStream in = urlConnection.getInputStream();
			BufferedImage input = ImageIO.read(in);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(input, "png", out);
			ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());
			OSSClient ossClient = new OSSClient("http://oss-cn-hangzhou.aliyuncs.com", "LTAImFOGCZqrqwdR", "BT1WPeeysx3pytUSfj5iTkRv02qGpe");
			ossClient.putObject("xylrs", "mouse/onlinetest/headImg/test.png", bis);
			in.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
	}
}
