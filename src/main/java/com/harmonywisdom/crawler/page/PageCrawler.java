package com.harmonywisdom.crawler.page;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.gamewolf.util.file.FileUtil;
import com.harmonywisdom.crawler.connection.HttpClientManager;
import com.harmonywisdom.crawler.proxy.Proxy;
import com.harmonywisdom.crawler.proxy.ProxyTap;
import com.harmonywisdom.crawler.proxy.ProxyTester;

public class PageCrawler {
	
	public List<String> pageUrl;
	public PageSelector selector;
	public ObjectPageBingding binding;
	public IInitializer initialize=null;
	public static HttpClientManager context=new HttpClientManager();
	
	private Proxy proxy=null;
	
	public void setInitializer(IInitializer init) {
		selector.setInitializer(init);
	}


	public void addPage(String url) {
		this.pageUrl.add(url);
	}
	
	public void clearPage() {
		this.pageUrl.clear();
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
	
	public List<Object> crawlWithProxy(Class clz,ProxyTap tap,ProxyTester tester){
		selector.setClz(clz);
		List<Object> list=new ArrayList<Object>();
		String res="";
		if(proxy==null) {
			proxy=tap.getProxy();
		}
		
		if(!tap.testProxy(proxy, tester)) {
			proxy=tap.getProxy();
		}
		
		for(String url:pageUrl) {
			res=context.fetchHTMLWithProxy(url, proxy);
			selector.setCont(res);
			selector.initialize();
			Object obj=selector.buildObject(binding);
			
			
			try {
				Method m=obj.getClass().getMethod("setUrl", String.class);
				m.invoke(obj, url);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				list.add(obj);
			}
		}
		return list;
	}
	
	public List<Object> crawlFile(Class clz){
		selector.setClz(clz);
		List<Object> list=new ArrayList<Object>();
		String res="";
		for(String url:pageUrl) {
			
			res=FileUtil.readTxtFile(url);
			
			
			selector.setCont(res);
			selector.initialize();
			Object obj=selector.buildObject(binding);
			
			
			try {
				Method m=obj.getClass().getMethod("setUrl", String.class);
				m.invoke(obj, url);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				list.add(obj);
			}
			
			
			
		}
		return list;
	}
	
	public List<Object> crawlByUrls(Class clz,List<String> urlList){
		selector.setClz(clz);
		List<Object> list=new ArrayList<Object>();
		for(String url:urlList) {
			String page=context.fetchHTML(url);
			selector.setCont(page);
			selector.initialize();
			Object obj=selector.buildObject(binding);
			try {
				CrawlerBeanUtil.SetPropValue("url", url, "String", obj);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			list.add(obj);
		}
		return list;
	}
	
	public List<Object> crawlByHtmlCont(Class clz,List<String> pageCont){
		selector.setClz(clz);
		List<Object> list=new ArrayList<Object>();
		for(String page:pageCont) {
			selector.setCont(page);
			selector.initialize();
			Object obj=selector.buildObject(binding);
			list.add(obj);
		}
		return list;
	}
	
	public List<Object> crawl(Class clz){
		selector.setClz(clz);
		List<Object> list=new ArrayList<Object>();
		String res="";
		for(String url:pageUrl) {
			res=context.fetchHTML(url);
			selector.setCont(res);
			selector.initialize();
			Object obj=selector.buildObject(binding);
			
			try {
				CrawlerBeanUtil.SetPropValue("url", url, "String", obj);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				list.add(obj);
			}			
		}
		return list;
	}
	
    

}
