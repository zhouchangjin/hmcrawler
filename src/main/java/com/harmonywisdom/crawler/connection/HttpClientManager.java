package com.harmonywisdom.crawler.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Date;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;

import com.harmonywisdom.crawler.httputil.HttpUrlUtil;

public class HttpClientManager {
	public static HttpClientConnectionManager connManager;
	
	CookieStore cookieStore;
	Header headers[];
	public HttpClientManager(){
		
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
	
	public String fetchHTML(String url) {
		
		
		try {
			HttpGet httpget=new HttpGet(url);
			buildCookieStore(url,httpget);
			  httpget.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Mobile Safari/537.36");
			  httpget.setHeader("Accept-Encoding","gzip, deflate, sdch");
			  httpget.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			  //httpget.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Mobile Safari/537.36");
			CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
			CloseableHttpResponse response=httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			StringBuffer sb=new StringBuffer();
		    if (entity != null) {
		    	String charset=entity.getContentType().toString();
		    	CharsetDecoder decoder=null;
		    	if(charset.contains("gb2312")) {
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
		System.out.println(contet.fetchHTML("http://product.m.dangdang.com/detail23812468-0-1.html"));
	}

}
