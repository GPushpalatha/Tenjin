package com.ycs.tenjincli.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {
	public static String trim(String str){
		if(str == null){return "";}
		return str.replace(String.valueOf((char) 160), " ").trim();
	}
	
	public static String getProperty(String propertyName) {
		String propertyValue = "";
		
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			ClassLoader classLoader = new Utilities().getClass().getClassLoader();
			input = classLoader.getResourceAsStream("console.properties");
			prop.load(input);
			propertyValue = prop.getProperty(propertyName);
			/*System.out.println("PROP VALUE FOUND [" + propertyName + "]--------------------- " + propertyValue);*/
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return trim(propertyValue);
	}
	
	public static void setPropertyValue(String propertyName, String propertyValue) {
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			//ClassLoader classLoader = new Utilities().getClass().getClassLoader();
			File propertiesFile = new File(new Utilities().getClass().getResource("tenjin.properties").getFile());
			input = new FileInputStream(propertiesFile);
			prop.load(input);
			/*if(prop == null) {
				System.out.println("Properties is null");
			}else{
				System.out.println("Properties are OK");
			}*/
			prop.setProperty(propertyName, propertyValue);
			OutputStream out = new FileOutputStream(propertiesFile);
			prop.store(out, "Updated on " + new Date());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] getParameters(String str) {
		List<String> list = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(str);
		while (m.find())
			list.add(m.group(1).replace("\"", "")); // Add .replace("\"", "") to remove surrounding quotes.
		
		return list.toArray(new String[0]);
	}
	
	public static String getRawTimeStamp(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String date = sdf.format(new Date());
		return date;
	}
	
	public static String getLogPathTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MMM_dd_hh_mm_ss");
		String date = sdf.format(new Date());
		return date; 
	}
	
	 
}
