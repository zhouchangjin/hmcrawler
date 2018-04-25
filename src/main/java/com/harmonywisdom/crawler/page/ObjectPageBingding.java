package com.harmonywisdom.crawler.page;

import java.util.HashMap;
import java.util.Iterator;

public class ObjectPageBingding {
	
	HashMap<String,String> nameBind;
	HashMap<String,String> typeBind; 
	HashMap<String,String> dataTypeBind;
	
	public ObjectPageBingding() {
		nameBind=new HashMap<String,String>();
		typeBind=new HashMap<String,String>();
		dataTypeBind=new HashMap<String,String>();
	}
	
	/**
	 * 
	 * @param prop
	 * @param xpath
	 * @param xpathType  抓取的是xpath tag内容还是tag属性 
	 * @param dataType   抓取的是String,Double，还是Int哦ger
	 */
	public void set(String prop,String xpath,String xpathType,String dataType) {
		this.nameBind.put(prop, xpath);
		this.typeBind.put(prop, xpathType);
		this.dataTypeBind.put(prop,dataType);
	}
	
	public String getXpath(String prop) {
		return nameBind.get(prop);
	}
	
	public String getXpathType(String prop) {
		return typeBind.get(prop);
	}
	
	public String getDataTypeBind(String prop) {
		return dataTypeBind.get(prop);
	}
	
	public Iterator<String> propertyIterator(){
		return nameBind.keySet().iterator();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
