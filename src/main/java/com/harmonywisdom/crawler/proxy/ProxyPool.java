package com.harmonywisdom.crawler.proxy;

import java.util.List;
import java.util.Stack;

import com.harmonywisdom.crawler.httputil.HtmlFetcher;

public class ProxyPool implements ProxyTap{
	
	Stack<Proxy> pool;
	
	String url;
	
	ResultParser resultParser;

	public ProxyPool(String url,ResultParser parser){
		pool=new Stack<Proxy>();
		this.url=url;
		this.resultParser=parser;
		
	}
	
	
	public void loadProxy() {
		String res=HtmlFetcher.FetchFromUrl(url);
		List<Proxy> list=resultParser.parse(res);
		for(Proxy p:list) {
			pool.push(p);
			System.out.println(p.getHost()+"-"+p.getPort());
		}
	}


	@Override
	public Proxy getProxy() {
		if(pool.isEmpty()) {
			loadProxy();
			if(pool.isEmpty()) {
				return null;
			}
		}
		
		Proxy p=pool.pop();
		System.out.println(p.getHost()+"==="+p.getPort());
		return p;
	}


	@Override
	public boolean testProxy(Proxy proxy,ProxyTester tester) {
		return tester.test(proxy);
		
	}
	


}
