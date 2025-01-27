package com.harmonywisdom.crawler.page;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class CrawlerBeanUtil {
	
	public static void SetPropValue(String propName,Object val,String dataType,Object object) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(object instanceof JSONObject) {
			JSONObject jsonObject=(JSONObject)object;
			if(dataType.equals("Double")) {
				try{

					double dbValue=Double.parseDouble(val.toString());
					jsonObject.put(propName, dbValue);
				}catch(Exception e) {
				}
			}else {
				jsonObject.put(propName, val);
			}
		}else {
			String propSetter = propName.substring(0, 1).toUpperCase()
					+ propName.substring(1);
			String value=val.toString();
		
			if(dataType.equals("String")) {
				Method m = object.getClass().getMethod("set" + propSetter, String.class);
				if(m!=null) {
					m.invoke(object, value);
				}
			}else if(dataType.equals("Integer")) {
				Method m = object.getClass().getMethod("set" + propSetter, Integer.class);
				if(m!=null) {
					m.invoke(object, Integer.parseInt(value));
				}
			}else if(dataType.equals("Double")) {
				Method m = object.getClass().getMethod("set" + propSetter, Double.class);
				if(m!=null) {
					if(value==null || value.trim().equals("")) {
						m.invoke(object, 0.0);
					}else {
						m.invoke(object, Double.parseDouble(value));
					}
					
				}				
			}else if(dataType.equals("List")) {
				Method m = object.getClass().getMethod("set" + propName, List.class);
				if(m!=null) {
					m.invoke(object,val);
				}
			}			

		}
	}

}
