package com.harmonywisdom.crawler.page;

import java.util.ArrayList;
import java.util.List;

import com.harmonywisdom.crawler.httputil.HtmlFetcher;

public class PageCrawler {
	
	public List<String> pageUrl;
	public PageSelector selector;
	public ObjectPageBingding binding;
	


	public void addPage(String url) {
		this.pageUrl.add(url);
	}

	public ObjectPageBingding getBinding() {
		return binding;
	}


	public void setBinding(ObjectPageBingding binding) {
		this.binding = binding;
	}

	
	public PageCrawler(){
		this.pageUrl=new ArrayList<String>();
		selector=new PageSelector();
	}
	
	
	public List<Object> crawl(Class clz){
		selector.setClz(clz);
		List<Object> list=new ArrayList<Object>();
		String res="";
		for(String url:pageUrl) {
			res=HtmlFetcher.FetchFromUrl(url);
			selector.setCont(res);
			selector.initialize();
			Object obj=selector.buildObject(binding);
			list.add(obj);
		}
		return list;
	}
	
    

}
