package com.jware.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import com.jware.exception.BaseException;

public class PropertiesLoader {

	 public String getPropertiesValue(String key) {
		 java.util.Properties props = new java.util.Properties();
		 try {
			props.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("com/jware/template/config.properties"),"UTF-8"));
		} catch (IOException e) {
			throw new BaseException(e);
		}
		 return (String)props.get(key);
	 }


	
	public  void setPropertiesValue(Map<String,String> config){
		 java.util.Properties props = new java.util.Properties();
		try {    
			Iterator iterator = config.keySet().iterator();
			while(iterator.hasNext()){
				String key = (String)iterator.next();
			    props.setProperty(key,config.get(key));    
			}
			FileOutputStream writer = new FileOutputStream(getClass().getClassLoader().getResource("com/jware/template/config.properties").getPath()); 
			props.store(writer, "UTF-8");
			writer.close(); 
			} catch (FileNotFoundException ex) {   
				throw new BaseException(ex);
			} catch (IOException ex) {    
				throw new BaseException(ex);
			} 
	}
	
	public static void main(String[] args) throws Exception{
		PropertiesLoader propertiesLoader = new PropertiesLoader();
	}
}
