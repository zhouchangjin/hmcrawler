package com.harmonywisdom.crawler.page;

public interface IPageSelector {
	String selectByXpath(String xpath);
	
	String selectAttribute(String path,String attributeName);
	
	String selectAttribute(String path);
	
	String selectRegExp(String reg);
	
	Object buildObject(ObjectPageBingding binding);
}
