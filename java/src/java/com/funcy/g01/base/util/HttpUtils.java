package com.funcy.g01.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class HttpUtils {

	        /**
    * 连接超时
    */
	private static int connectTimeOut = 5000;

	        /**
    * 读取数据超时
    */
	private static int readTimeOut = 10000;
	
	/**
	 * 超时处理时间
	 */
	private final static int overTimeOut = 2000;

	        /**
    * 请求编码
    */
	private static String requestEncoding = "utf-8";

	private static Logger logger = Logger.getLogger(HttpUtils.class);


	        /**
    * @return 连接超时(毫秒)
    * @see com.hengpeng.common.web.HttpRequestProxy#connectTimeOut
    */
	public static int getConnectTimeOut() {
		return HttpUtils.connectTimeOut;
	}

	        /**
    * @return 读取数据超时(毫秒)
    * @see com.hengpeng.common.web.HttpRequestProxy#readTimeOut
    */
	public static int getReadTimeOut() {
		return HttpUtils.readTimeOut;
	}

	        /**
    * @return 请求编码
    * @see com.hengpeng.common.web.HttpRequestProxy#requestEncoding
    */
	public static String getRequestEncoding() {
		return requestEncoding;
	}

	        /**
    * @param connectTimeOut 连接超时(毫秒)
    * @see com.hengpeng.common.web.HttpRequestProxy#connectTimeOut
    */
	public static void setConnectTimeOut(int connectTimeOut) {
		HttpUtils.connectTimeOut = connectTimeOut;
	}

	        /**
    * @param readTimeOut 读取数据超时(毫秒)
    * @see com.hengpeng.common.web.HttpRequestProxy#readTimeOut
    */
	public static void setReadTimeOut(int readTimeOut) {
		HttpUtils.readTimeOut = readTimeOut;
	}

	        /**
    * @param requestEncoding 请求编码
    * @see com.hengpeng.common.web.HttpRequestProxy#requestEncoding
    */
	public static void setRequestEncoding(String requestEncoding) {
		HttpUtils.requestEncoding = requestEncoding;
	}
	
	
	        /**
    * <pre>
    * 发送带参数的GET的HTTP请求
    * </pre>
    * 
    * @param reqUrl HTTP请求URL
    * @param parameters 参数映射表
    * @return HTTP响应的字符串
    */
	public static String doGet(String reqUrl, Map<?, ?> parameters, String recvEncoding) {
		String result = null;
		Future<?> future = null;
		LongTimeHttpPool httpPool = LongTimeHttpPool.getInstance();
		try {
			future = httpPool.execute(new Callable<String>() {
				@Override
				public String call() {
					return doGet0(reqUrl, parameters, recvEncoding);
				}
			});
			result = future.get(overTimeOut, TimeUnit.MILLISECONDS).toString();
		} catch (Exception e) {
			logger.error("获取数据出错"+reqUrl, e);
			future.cancel(true);
		}
		return result;
	}
	
	private static String doGet0(String reqUrl, Map<?, ?> parameters, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator<?> iter = parameters.entrySet().iterator(); iter.hasNext();) {
				Entry<?, ?> element = (Entry<?, ?>) iter.next();
                if (element.getValue() != null) {
                    params.append(element.getKey().toString());
                    params.append("=");
                    params.append(URLEncoder.encode(element.getValue().toString(),
                                                    HttpUtils.requestEncoding));
                    params.append("&");
                }
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}
			
			if(reqUrl.indexOf("?") != -1){
				reqUrl += "&" + params;
			}else{
				reqUrl += "?" + params;
			}

			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
//            System.setProperty("sun.net.client.defaultConnectTimeout",
//                               String.valueOf(HttpUtils.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
//            System.setProperty("sun.net.client.defaultReadTimeout",
//                               String.valueOf(HttpUtils.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
			url_con.setConnectTimeout(HttpUtils.connectTimeOut);//（单位：毫秒）jdk
			url_con.setReadTimeout(HttpUtils.readTimeOut);//（单位：毫秒）jdk 1.5换成这个,读操作超时
//			url_con.setDoOutput(true);
//			byte[] b = params.toString().getBytes();
//			url_con.getOutputStream().write(b, 0, b.length);
//			url_con.getOutputStream().flush();
//			url_con.getOutputStream().close();

			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer temp = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				temp.append(tempLine);
				temp.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = temp.toString();
			rd.close();
			in.close();
		}catch (SocketTimeoutException e){
			logger.error("连接超时"+reqUrl,e);
		}
		catch (IOException e) {
            logger.error("网络故障", e);
		}
		finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}

		return responseContent;
	}

	        /**
    * <pre>
    * 发送不带参数的GET的HTTP请求
    * </pre>
    * 
    * @param reqUrl HTTP请求URL
    * @return HTTP响应的字符串
    */
	public static String doGet(String reqUrl, String recvEncoding) {
		String result = null;
		Future<?> future = null;
		LongTimeHttpPool httpPool = LongTimeHttpPool.getInstance();
		try {
			future = httpPool.execute(new Callable<String>() {
				@Override
				public String call() {
					return doGet0(reqUrl, recvEncoding);
				}
			});
			result = future.get(overTimeOut, TimeUnit.MILLISECONDS).toString();
		} catch (Exception e) {
			logger.error("获取数据出错"+reqUrl, e);
			future.cancel(true);
		}
		return result;
	}
	
	private static String doGet0(String reqUrl, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			String queryUrl = reqUrl;
			int paramIndex = reqUrl.indexOf("?");

			if (paramIndex > 0) {
				queryUrl = reqUrl.substring(0, paramIndex);
				String parameters = reqUrl.substring(paramIndex + 1, reqUrl.length());
				String[] paramArray = parameters.split("&");
				for (int i = 0; i < paramArray.length; i++) {
					String string = paramArray[i];
					int index = string.indexOf("=");
					if (index > 0) {
						String parameter = string.substring(0, index);
						String value = string.substring(index + 1, string.length());
						params.append(parameter);
						params.append("=");
						params.append(URLEncoder.encode(value, HttpUtils.requestEncoding));
						params.append("&");
					}
				}

				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(queryUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
//            System.setProperty("sun.net.client.defaultConnectTimeout",
//                               String.valueOf(HttpUtils.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
//            System.setProperty("sun.net.client.defaultReadTimeout",
//                               String.valueOf(HttpUtils.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
			url_con.setConnectTimeout(HttpUtils.connectTimeOut);//（单位：毫秒）jdk
			url_con.setReadTimeout(HttpUtils.readTimeOut);//（单位：毫秒）jdk 1.5换成这个,读操作超时
            // url_con.setReadTimeout(5000);//（单位：毫秒）jdk 1.5换成这个,读操作超时
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer temp = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				temp.append(tempLine);
				temp.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = temp.toString();
			rd.close();
			in.close();
		}catch (SocketTimeoutException e){
			logger.error("连接超时"+reqUrl,e);
		}
		catch (IOException e) {
            logger.error("网络故障", e);
		}
		finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}

		return responseContent;
	}

	        /**
    * <pre>
    * 发送带参数的POST的HTTP请求
    * </pre>
    * 
    * @param reqUrl HTTP请求URL
    * @param parameters 参数映射表
    * @return HTTP响应的字符串
    */
	public static String doPost(String reqUrl, Map<String, Object> parameters, String recvEncoding) {
		String result = null;
		Future<?> future = null;
		LongTimeHttpPool httpPool = LongTimeHttpPool.getInstance();
		try {
			future = httpPool.execute(new Callable<String>() {
				@Override
				public String call() {
					return doPost0(reqUrl, parameters, recvEncoding);
				}
			});
			result = future.get(overTimeOut, TimeUnit.MILLISECONDS).toString();
		} catch (Exception e) {
			logger.error("获取数据出错"+reqUrl, e);
			future.cancel(true);
		}
		return result;
	}
	
	private static String doPost0(String reqUrl, Map<String, Object> parameters, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator<?> iter = parameters.entrySet().iterator(); iter.hasNext();) {
				Entry<?, ?> element = (Entry<?, ?>) iter.next();
                if (element.getValue() != null) {
                    params.append(element.getKey().toString());
                    params.append("=");
                    params.append(URLEncoder.encode(element.getValue().toString(),
                                                    HttpUtils.requestEncoding));
                    params.append("&");
                }
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
//            System.setProperty("sun.net.client.defaultConnectTimeout",
//                               String.valueOf(HttpUtils.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
//            System.setProperty("sun.net.client.defaultReadTimeout",
//                               String.valueOf(HttpUtils.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
            url_con.setConnectTimeout(HttpUtils.connectTimeOut);//（单位：毫秒）jdk
//             1.5换成这个,连接超时
            url_con.setReadTimeout(HttpUtils.readTimeOut);//（单位：毫秒）jdk 1.5换成这个,读操作超时
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();

			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
		}catch (SocketTimeoutException e){
			logger.error("连接超时"+reqUrl,e);
		}
		catch (IOException e) {
            logger.error("网络故障", e);
		}
		finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		return responseContent;
	}
	
	
	public static String postXML(String urlStr,Map<String, String> paramMap){
		try {  
            URL url = new URL(urlStr);  
            URLConnection con = url.openConnection();  
            con.setDoOutput(true);  
            con.setRequestProperty("Cache-Control", "no-cache");  
            con.setRequestProperty("Content-Type", "text/xml");  
  
            OutputStreamWriter out = new OutputStreamWriter(con  
                    .getOutputStream());      
            String xmlInfo = getXmlInfo(paramMap);
            out.write(new String(xmlInfo.getBytes("UTF-8")));  
            out.flush();  
            out.close();  
            BufferedReader br = new BufferedReader(new InputStreamReader(con  
                    .getInputStream()));  
            String line = "";  
            String result = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {  
                result += line + "\n";
            }  
            return result;
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		return null;
	}

	
	private static String getXmlInfo(Map<String, String> paramMap) {
		if(paramMap != null && paramMap.size() > 0){
			StringBuffer sb = new StringBuffer();
			sb.append("<xml>");
			
			for(String key:paramMap.keySet()){
				sb.append("<"+key+">").append(paramMap.get(key)+"</"+key+">");
			}
			sb.append("</xml>");
			return sb.toString();
		}
		return null;
	}

	public static  String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("RealIP");
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(StringUtils.isBlank(ip)){
            ip = "127.0.0.1";//实在是判断不出IP了...
		}
		return ip;
	}

	
	public static void main(String[] args) {
		try {  
            URL url = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");  
            URLConnection con = url.openConnection();  
            con.setDoOutput(true);  
            con.setRequestProperty("Pragma:", "no-cache");  
            con.setRequestProperty("Cache-Control", "no-cache");  
            con.setRequestProperty("Content-Type", "text/xml");  
  
            OutputStreamWriter out = new OutputStreamWriter(con  
                    .getOutputStream());      
            String xmlInfo = ("<xml>"
	+"<appid>wx16b6e2627f76466a</appid>"
	+"<body>123</body>"
	+"<device_info>1</device_info>"
	+"<fee_type>CNY</fee_type>"
	+"<mch_id>1229276402</mch_id>"
	+"<nonce_str>ea29591cfde64e0a858f116663fed846</nonce_str>"
	+"<notify_url>http://zhangle.shopweb.diguread.com/weixin/notify</notify_url>"
	+"<openid>oA0Jds8X0o_cUutPm_2LQkIs0da0</openid>"
	+"<out_trade_no>100978</out_trade_no>"
	+"<spbill_create_ip>183.16.151.158</spbill_create_ip>"
	+"<total_fee>21300</total_fee>"
	+"<trade_type>JSAPI</trade_type>"
	+"<sign>DFDD0356F4C50F759ACA2B8782BF973D</sign>"
+"</xml>");
            out.write(new String(xmlInfo.getBytes("UTF-8")));  
            out.flush();  
            out.close();  
            BufferedReader br = new BufferedReader(new InputStreamReader(con  
                    .getInputStream()));  
            String line = "";  
            String result = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {  
                result += line + "\n";
            }  
            System.out.println(result);
        } catch (MalformedURLException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
	}
}
