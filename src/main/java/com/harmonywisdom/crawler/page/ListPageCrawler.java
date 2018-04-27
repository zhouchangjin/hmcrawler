package com.harmonywisdom.crawler.page;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.harmonywisdom.crawler.httputil.HtmlFetcher;

public class ListPageCrawler {
	public static final String pageTemp="{page}";
	public String listUrl;
	public int maxPageCount;
	public String className;
	public String xpath;
	public ObjectPageBingding binding;
	public IOutput output;
	public IInitializer initialize;
	Class clz;

	
	public IInitializer getInitialize() {
		return initialize;
	}

	public void setInitialize(IInitializer initialize) {
		this.initialize = initialize;
		selector.setInitializer(initialize);
	}
	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
		try {
			clz=Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			clz=null;
			e.printStackTrace();
			return;
		}
	}

	public ListPageSelector selector;

	public String getListUrl() {
		return listUrl;
	}

	public void setListUrl(String listUrl) {
		this.listUrl = listUrl;
	}

	public int getMaxPageCount() {
		return maxPageCount;
	}

	public void setMaxPageCount(int maxPageCount) {
		this.maxPageCount = maxPageCount;
	}

	public ListPageCrawler(){
		selector=new ListPageSelector();
		
		
	}
	
	public void crawl(int startPage) {
		String url=listUrl;
		for(int i=startPage;i<=maxPageCount;i++) {
			url=listUrl.replace(pageTemp,""+i);
			
			System.out.println("采集第"+i+"页中.....");
			String res=HtmlFetcher.FetchFromUrl(url);
			//System.out.println(res);
			selector.setCont(res);
			selector.setClz(clz);
			selector.initialize();
			List<IPageSelector> list=selector.selectItems(xpath);
			for(IPageSelector sel:list) {
				Object obj=sel.buildObject(binding);
				
				try {
					Method m=obj.getClass().getMethod("setPage", Integer.class);
					m.invoke(obj, i);
				} catch (NoSuchMethodException e) {
					System.out.println("无page属性");
					continue;
					//e.printStackTrace();
				} catch (SecurityException e) {
					
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					continue;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}finally {
					output.output(obj);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public ObjectPageBingding getBinding() {
		return binding;
	}

	public void setBinding(ObjectPageBingding binding) {
		this.binding = binding;
	}

	public IOutput getOutput() {
		return output;
	}

	public void setOutput(IOutput output) {
		this.output = output;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
