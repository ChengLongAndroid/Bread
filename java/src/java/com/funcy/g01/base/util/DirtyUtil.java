package com.funcy.g01.base.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.funcy.g01.base.exception.BusinessException;
import com.funcy.g01.base.exception.ErrorCode;


public class DirtyUtil {
	
	private static final Logger logger = Logger.getLogger(DirtyUtil.class);
	
	private static final List<Pattern> patterns = new ArrayList<Pattern>();
	
	private static HashMap<String, String> config = new HashMap<String, String>();
	
	private static String filename = "sysconfig.properties";
	
	static
	{
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		InputStream input = cl.getResourceAsStream(filename);

		if (input != null)
		{
			try
			{
				BufferedReader URLinput = new BufferedReader(new InputStreamReader(input, "utf-8"));
				String line = null;
				while ((line = URLinput.readLine()) != null)
				{
					if (line.trim().length() != 0)
					{
						int index = line.indexOf('=');
						if (index > 0)
						{
							String name = line.substring(0, index).trim();
							String value = line.substring(index + 1).trim();
							if(name.equals("DIRTY_WORD")){
								String[] words = value.split(";");
								for(String word : words){
									patterns.add(Pattern.compile(word.replace("*", "\\*").replace("+", "\\+").replace("?", "\\?").replace("(", "\\(").replace(")", "\\)")));
								}
							} else {
								config.put(name, value);
							}
						}
					}
				}
				URLinput.close();
				input.close();

			}
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				System.err.println("配置文件" + filename + "找不到！");
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				System.err.println("读取配置文件" + filename + "错误！");
				e.printStackTrace();
			}
		}
	}
	
	public static void init(List<String> regexes){
		patterns.clear();
		for(String regex : regexes){
			String str1 = filterRegex( regex);
			Pattern pattern = Pattern.compile(str1);
			patterns.add(pattern);
		}
	}
	
	public static void match(String str){
		int index = 1;
		for(Pattern pattern : patterns){
			Matcher matcher = pattern.matcher(str);
			if(matcher.find()){
				logger.info("输入的是："+str+",匹配的脏字是多少行:"+index);
				throw new BusinessException(ErrorCode.DIRTY_WORD,"dirty word");
			}
			index++;
		}
	}
	
	public static String replace(String str){
		for(Pattern pattern : patterns){
			Matcher matcher = pattern.matcher(str);
			str = matcher.replaceAll("*");
		}
		return str;
	}

	private static String filterRegex(String newRegex) {
		String str1;
		String str2 = newRegex.replace("*", "\\*");
		String str3 = str2.replace(".","\\.");
		String str4 = str3.replace("(", "\\(");
		String str5 = str4.replace(")", "\\)");
		str1 = str5;
		return str1;
	}
}
