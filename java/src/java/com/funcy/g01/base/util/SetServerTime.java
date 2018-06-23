package com.funcy.g01.base.util;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;
@Component
public class SetServerTime {

	public void set(Map<Object,Object> map){
		String yyyy = String.valueOf(map.get("yyyy"));
		String MM = String.valueOf(map.get("MM"));
		String dd = String.valueOf(map.get("dd"));
		String hh = String.valueOf(map.get("hh"));
		String mm = String.valueOf(map.get("mm"));
		String ss = String.valueOf(map.get("ss"));
		String osName = System.getProperty("os.name");  
		String cmd = "";  
		try {  
			if (osName.matches("^(?i)Windows.*$")) {// Window 绯荤粺  
				// 鏍煎紡 HH:mm:ss  
				cmd = "  cmd /c time "+hh+":"+mm+":"+ss;  
				Runtime.getRuntime().exec(cmd);  
				// 鏍煎紡锛歽yyy-MM-dd  
				cmd = " cmd /c date "+yyyy+"-"+MM+"-"+dd;  
				Runtime.getRuntime().exec(cmd);  
			} else {// Linux 绯荤粺  
				// 鏍煎紡锛歽yyyMMdd  
				cmd = "  date -s "+yyyy+MM+dd;  
				Runtime.getRuntime().exec(cmd);  
				// 鏍煎紡 HH:mm:ss  
				cmd = "  date -s "+hh+":"+mm+":"+ss;  
				Runtime.getRuntime().exec(cmd);  
			}  
		} catch (IOException e) {  	
			e.printStackTrace();  
		}  
	}
	
}
