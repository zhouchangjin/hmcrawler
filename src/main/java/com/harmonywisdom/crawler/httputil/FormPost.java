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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

public class FormPost {
	
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
