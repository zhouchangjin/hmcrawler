package com.harmonywisdom.crawler.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

import com.harmonywisdom.crawler.httputil.HttpUrlUtil;
import com.harmonywisdom.crawler.proxy.Proxy;

public class HttpClientManager {
	public static HttpClientConnectionManager connManager;
	
	CookieStore cookieStore;
	Header headers[];
	
	String host="";
	
	String referer="";
	
	Map<String,String> requestHeaderMap;
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}



	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public HttpClientManager(){
		requestHeaderMap=new HashMap<String, String>();
	}
	
	public void addCommonHeadder(String key,String value) {
		requestHeaderMap.put(key, value);
	}
	
	void buildCookieStore(String url,HttpGet httpget) {
		cookieStore=new BasicCookieStore();
		if(headers==null) {
			return;
		}
		for(Header cookieheader:headers) {
			HeaderElement[] ele=cookieheader.getElements();
			for(int i=0;i<ele.length;i++){
				String value=ele[i].getValue();
				String name=ele[i].getName();
				BasicClientCookie cookie = new BasicClientCookie(name, value);
				if(ele[i].getParameterByName("Path")!=null) {
					cookie.setPath(ele[i].getParameterByName("Path").getValue());
				}else {
					cookie.setPath("/");
				}
				cookie.setDomain(HttpUrlUtil.getHost(url));
				cookie.setVersion(0);
				Date d=new Date();
				d.setTime(d.getTime()+24*60*60*1000);
				cookie.setExpiryDate(d);
				cookieStore.addCookie(cookie);
				httpget.setHeader(name, value);
			}
		}


	}
	
	public void login(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget=new HttpGet(url);
		httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
		httpget.setHeader("Accept-Encoding","gzip, deflate, sdch");
		httpget.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpget);
			headers=response.getHeaders("Set-Cookie");
			return;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

	}
	
	public String formPost(String url,HashMap<String,String> form,Header cookieheader,String host) {
		String buffer="";
		HttpPost httpPost=new HttpPost(url);
		HeaderElement[] ele=cookieheader.getElements();
		for(int i=0;i<ele.length;i++){
			String value=ele[i].getValue();
			String name=ele[i].getName();
			BasicClientCookie cookie = new BasicClientCookie(name, value);
			cookie.setPath(ele[i].getParameterByName("Path").getValue());
			cookie.setDomain(host);
			cookie.setVersion(0);
			Date d=new Date();
			d.setTime(d.getTime()+24*60*60*1000);
			cookie.setExpiryDate(d);
			cookieStore.addCookie(cookie);
			httpPost.setHeader(name, value);
		}
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		for(String key:form.keySet()) {
			BasicNameValuePair pair=new BasicNameValuePair(key, form.get(key));
			list.add(pair);
		}
		UrlEncodedFormEntity formentity = new UrlEncodedFormEntity(list, Consts.UTF_8);
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		
		httpPost.setEntity(formentity);
		try {
			CloseableHttpResponse response=httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
		    if (entity != null) {
		        InputStream instream = entity.getContent();
		        BufferedReader br=new BufferedReader(new InputStreamReader(instream));
		        try {
		            // do something useful
		        	
		        	String line="";
		        	while((line=br.readLine())!=null){
		        		System.out.println(line);
		        		buffer+=line+"\n";
		        	}
		        } finally {
		            instream.close();
		        }
		    }
		    return buffer;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buffer;
	}
	
	public String fetchHTMLWithProxy(String url,Proxy p)  {
		HttpHost proxy = new HttpHost(p.getHost(), p.getPort());
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		
		CloseableHttpClient httpclient = HttpClients.custom()
		        .setRoutePlanner(routePlanner)
		        .build();
		
		HttpGet httpget=new HttpGet(url);
		try {
			CloseableHttpResponse response=httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			StringBuffer sb=new StringBuffer();
			 if (entity != null) {
			        InputStream instream = entity.getContent();
			        BufferedReader br=new BufferedReader(new InputStreamReader(instream));
			        try {
			            // do something useful
			        	String line="";
			        	while((line=br.readLine())!=null){
			        		//System.out.println(line);
			        		sb.append(line);
			        	}
			        	return sb.toString();
			        } finally {
			            instream.close();
			        }
			    }
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return "";
	}
	
	public String fetchHTML(String url) {
		
		
		try {
			HttpGet httpget=new HttpGet(url);
			buildCookieStore(url,httpget);
			//httpget.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Mobile Safari/537.36");
			//httpget.setHeader("User-Agent","MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");  
			//httpget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");
			httpget.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0");
			httpget.setHeader("Accept-Encoding","gzip, deflate, sdch");
			httpget.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			//不要随便加下面的这一行
			//httpget.setHeader("Host", HttpUrlUtil.getHost(url));
			if(host!=null && !host.equals("")) {
				System.out.println("设置host");
				httpget.setHeader("Host", host);
			}
			
			if(referer!=null && !referer.equals("")) {
				System.out.println("设置refer");
				httpget.setHeader("Referer", referer);
			}
			for(String key:requestHeaderMap.keySet()) {
				httpget.setHeader(key, requestHeaderMap.get(key));
			}
			  
			CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			CloseableHttpResponse response=httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			StringBuffer sb=new StringBuffer();
		    if (entity != null) {
		    	String charset=entity.getContentType().toString();
		    	CharsetDecoder decoder=null;
		    	if(charset.contains("gb2312")) {
		    		decoder=Charset.forName("GBK").newDecoder();
		    	}else if(charset.contains("gbk")) {
		    		decoder=Charset.forName("GBK").newDecoder();
		    	}
		        InputStream instream = entity.getContent();
		        BufferedReader br=null;
		        if(decoder==null) {
		        	br=new BufferedReader(new InputStreamReader(instream));
		        }else {
		        	br=new BufferedReader(new InputStreamReader(instream,decoder)); 
		        }
		        
		        try {
		            // do something useful
		        	String line="";
		        	while((line=br.readLine())!=null){
		        		//System.out.println(line);
		        		sb.append(line);
		        	}
		        	return sb.toString();
		        } finally {
		            instream.close();
		        }
		    }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return "";
	}
	
	public static void main(String args[]) {
		HttpClientManager contet=new HttpClientManager();
		contet.login("http://product.m.dangdang.com/detail23812468-0-1.html");
		System.out.println(contet.fetchHTML("http://product.m.dangdang.com/1325787266.html"));
	}

}
