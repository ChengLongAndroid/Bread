package com.funcy.g01.base.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.funcy.g01.base.data.SpecialCityData;

import net.sf.json.JSONObject;

public class MapUtil {
	
	private static final String ak = "LAhgUZv4qIC4XcuaGc9M4MZuGN7xcY6D"; //百度开发者密钥
	
	public static void main(String[] args) {
		String respStr = getAddressInformation("22.54871828226","113.94121482683",null);
		System.out.println(respStr);
	}
	
	/**
	 * 根据经纬度获得地址信息
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @return 城市名称
	 */
	@SuppressWarnings("unchecked")
	public static String getAddressInformation(String latitude,String longitude,SpecialCityData specialCityData){
		try {
			String url = "http://api.map.baidu.com/geocoder/v2/?";
			//经纬度处理
			String coords = longitude + "," + latitude;
//			String coords = "106.5571393991" + "," + "29.5710002003";
			String location = transformBaiduCoordinate(coords);
			//请求参数
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("output", "json"); //输出格式
			params.put("ak", ak);		
			params.put("location", location);	//经纬度
			params.put("extensions_poi", null);	//extensions_poi=null时，底层不调用poi相关服务，可减少服务访问时延
			params.put("latest_admin", 1);
			
			String respStr = HttpUtils.doPost(url, params, "utf-8");
			JSONObject jsonObject = JSONObject.fromObject(respStr);
			Map<String, Map<String,Object>> result = (Map<String, Map<String,Object>>)jsonObject.get("result");
			Map<String, Object> addressComponent = result.get("addressComponent");
			String province = (String)addressComponent.get("province");
			String city = (String)addressComponent.get("city");
			if(specialCityData != null && specialCityData.isSpecialCity(province, city)){
				if("香港".equals(city) || "澳门".equals(city) || "台湾".equals(city)){
					city = "中国" + city;
				}
				return city;
			}
			return province;
		} catch(Exception e) {
			e.printStackTrace();
			return "火星";
		}
	}
	
	/**
	 * 多渠道经纬度  -> 百度地图坐标
	 * @param coords 源坐标
	 * @return 转换后的坐标字符串
	 */
	@SuppressWarnings("unchecked")
	public static String transformBaiduCoordinate(String coords){
		String url = "http://api.map.baidu.com/geoconv/v1/?";
		String x = null;
		String y = null;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("coords", coords);
		params.put("ak", ak);
		params.put("from", 1);	//GPS设备获取的坐标
		params.put("to", 5);		//百度经纬度
		params.put("output", "json");   //输出格式
		String respStr = HttpUtils.doPost(url, params, "utf-8");
		JSONObject jsonObject = JSONObject.fromObject(respStr);
		List<Map<String, Double>> result = (List<Map<String, Double>>)jsonObject.get("result");
		Map<String, Double> coordsMap = result.get(0);
		x = String.valueOf(coordsMap.get("x"));
		y = String.valueOf(coordsMap.get("y"));
		return y + "," + x;
	}
}
