package com.harmonywisdom.crawler.page;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.harmonywisdom.crawler.connection.HttpClientManager;

public class JSONPageCrawler {
	
	public List<String> pageUrl;
	public IInitializer initialize=null;
	public static HttpClientManager context=new HttpClientManager();
	public JSONSelector selector;
	public JSONObjectBinding binding;
	
	
	public JSONObjectBinding getBinding() {
		return binding;
	}
	public void setBinding(JSONObjectBinding binding) {
		this.binding = binding;
	}
	public JSONSelector getSelector() {
		return selector;
	}
	public void setSelector(JSONSelector selector) {
		this.selector = selector;
	}
	public List<String> getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(List<String> pageUrl) {
		this.pageUrl = pageUrl;
	}
	public IInitializer getInitialize() {
		return initialize;
	}
	public void setInitialize(IInitializer initialize) {
		this.initialize = initialize;
	}
	public static HttpClientManager getContext() {
		return context;
	}
	public static void setContext(HttpClientManager context) {
		JSONPageCrawler.context = context;
	}
	
	public void addPage(String url) {
		this.pageUrl.add(url);
	}
	
	public void clearPage() {
		this.pageUrl.clear();
	}
	
	public JSONPageCrawler() {
		this.pageUrl=new ArrayList<String>();
		selector=new JSONSelector();
	}
	
	public List<Object> crawl(Class clz){
		
		List<Object> list=new ArrayList<Object>();
		String res="";

		for(String url:pageUrl) {
			res=context.fetchHTML(url);
			//System.out.println(res);
			if(res.contains("ErrorCode")) {
				continue;
			}
			Gson gson=new Gson();
			Object obj=gson.fromJson(res, clz);
			list.add(obj);
			if(this.binding!=null) {
				selector.setCont(res);
				selector.initialize();
				selector.assemble(obj, binding);
			}
		}
		return list;
	}

}
