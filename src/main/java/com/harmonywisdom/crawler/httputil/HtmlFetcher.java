package com.harmonywisdom.crawler.httputil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HtmlFetcher {
	public static String FetchHtml(String url,String... params){
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		
		try {
			URIBuilder builder = new URIBuilder().setPath(url);
			for(String param:params){
				String pair[]=param.split("=");
				if(pair.length==2){
					builder.addParameter(pair[0], pair[1]);
				}else{
					System.out.println(param+"格式有问题");
					return "";
				}
				
			}
			URI uri=builder.build();
			HttpGet httpget=new HttpGet(uri);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}
	
	public static InputStream getInputSteam(String url,String... params){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		URIBuilder builder = new URIBuilder().setPath(url);
		for(String param:params){
			String pair[]=param.split("=");
			if(pair.length==2){
				builder.addParameter(pair[0], pair[1]);
			}else{
				System.out.println(param+"格式有问题");
				return null;
			}
			
		}
		URI uri;
		try {
			uri = builder.build();
			HttpGet httpget=new HttpGet(uri);
			CloseableHttpResponse response=httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			return entity.getContent();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}	
	public static String FetchHtml(String url){

		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		
		try {
			URI uri = new URIBuilder().setPath(url).build();
			HttpGet httpget=new HttpGet(uri);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		
	}

}
