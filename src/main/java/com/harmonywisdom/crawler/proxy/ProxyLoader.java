package com.harmonywisdom.crawler.proxy;

import java.util.List;

import com.harmonywisdom.crawler.httputil.HtmlFetcher;



public class ProxyLoader {
	
	public String url;
	public ResultParser parser;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public ProxyLoader(String url,ResultParser parser){
		this.url=url;
		this.parser=parser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	public List<Proxy> getIpList() {
		String res=HtmlFetcher.FetchFromUrl(url);
		List<Proxy> list=parser.parse(res);
		return list;
	}

}
