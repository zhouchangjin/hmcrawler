package com.harmonywisdom.crawler.page;

import java.util.List;

import org.w3c.dom.Node;

public interface IPageSelector {
	String selectByXpath(String xpath);
	
	String selectByXpath(String xpath,Node context);
	
	String selectAttribute(String path,String attributeName);
	
	String selectAttribute(String path);
	
	List<String> selectAttributes(String path);
	
	List<String> selectAttributes(String path,Node context);
	
	String selectAttribute(String path,Node context);
	
	String selectContentByXpath(String xpath);
	
	String selectContentByXpath(String xpath,Node context);
	
	String selectRegExp(String reg);
	
	String selectRegExp(String reg,Node context);
	
	Object buildObject(ObjectPageBingding binding);
}
