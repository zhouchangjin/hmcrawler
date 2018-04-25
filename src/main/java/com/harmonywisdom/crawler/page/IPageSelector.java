package com.harmonywisdom.crawler.page;

public interface IPageSelector {
	String selectByXpath(String xpath);
	
	String selectAttribute(String path,String attributeName);
}
