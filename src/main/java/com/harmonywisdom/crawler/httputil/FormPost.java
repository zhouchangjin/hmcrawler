package com.harmonywisdom.crawler.httputil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

public class FormPost {
	
	public static Header getLogin(String url) {
		
		HttpGet httpget=new HttpGet(url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		try {
			CloseableHttpResponse response=httpclient.execute(httpget);
			
			Header headers[]=response.getAllHeaders();
			for(Header h:headers) {
				//System.out.println(h.toString());
			}
			Header header=response.getFirstHeader("Set-Cookie");
			
			HttpEntity entity = response.getEntity();
		    if (entity != null) {
		        InputStream instream = entity.getContent();
		        BufferedReader br=new BufferedReader(new InputStreamReader(instream));
		        try {
		            // do something useful
		        	String line="";
		        	while((line=br.readLine())!=null){
		        		//System.out.println(line);
		        	}
		        } finally {
		            instream.close();
		        }
		    }
		    return header;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Header login(String url,Map<String,String> map) {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		for(String key:map.keySet()) {
			
			formparams.add(new BasicNameValuePair(key, map.get(key)));
		}
		UrlEncodedFormEntity formentity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost=new HttpPost(url);
		httpPost.setEntity(formentity);
		try {
			CloseableHttpResponse response=httpclient.execute(httpPost);
			Header header=response.getFirstHeader("Set-Cookie");
			
			HttpEntity entity = response.getEntity();
		    if (entity != null) {
		        InputStream instream = entity.getContent();
		        BufferedReader br=new BufferedReader(new InputStreamReader(instream));
		        try {
		            // do something useful
		        	String line="";
		        	while((line=br.readLine())!=null){
		        		//System.out.println(line);
		        	}
		        } finally {
		            instream.close();
		        }
		    }
		    return header;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String formPostFromMapWithHeader(String url,Map<String,String> body,Map<String,String> header,Header cookieheader) {
		CookieStore cookieStore = new BasicCookieStore();
		String buffer="";
		HttpPost httpPost=new HttpPost(url);
		List<NameValuePair> formparams=new ArrayList<NameValuePair>();
		for(String key:body.keySet()) {
			BasicNameValuePair pair=new BasicNameValuePair(key, body.get(key));
			formparams.add(pair);
		}
		
		
		for(String key:header.keySet()) {
			BasicHeader bh=new BasicHeader(key, header.get(key));
			httpPost.addHeader(bh);
		}
		if(cookieheader!=null) {
			
			HeaderElement[] ele=cookieheader.getElements();
			for(int i=0;i<ele.length;i++){
				String value=ele[i].getValue();
				String name=ele[i].getName();
				BasicClientCookie cookie = new BasicClientCookie(name, value);
				cookie.setPath(ele[i].getParameterByName("Path").getValue());
				if(header.containsKey("Host")) {
					cookie.setDomain(header.get("Host"));
				}
				cookie.setVersion(0);
				Date d=new Date();
				d.setTime(d.getTime()+24*60*60*1000);
				cookie.setExpiryDate(d);
				cookieStore.addCookie(cookie);
				httpPost.setHeader(name, value);
			}
		}
		UrlEncodedFormEntity formentity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
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
		        		//System.out.println(line);
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
	
	public static String formPostFromMap(String url,Map<String,String> formMap,Header cookieheader,String host) {
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		for(String key:formMap.keySet()) {
			BasicNameValuePair pair=new BasicNameValuePair(key, formMap.get(key));
			list.add(pair);
		}
		return formPost(url,list,cookieheader,host);
	}
	
	
	public static String formPost(String url,List<NameValuePair> formparams,Header cookieheader,String host){
		String buffer="";
		CookieStore cookieStore = new BasicCookieStore();
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
		
		UrlEncodedFormEntity formentity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		
		BasicHeader bh=new BasicHeader("Host",	host);
		httpPost.setHeader(bh);
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

}
