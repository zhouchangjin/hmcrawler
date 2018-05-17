package com.harmonywisdom.crawler.httputil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;

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
	
	public static String FetchFromUrlWithProxy(String urlWithParameters,String host,int port) {
		
		HttpHost proxy = new HttpHost(host, port);
		DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
		CloseableHttpClient httpclient = HttpClients.custom()
		        .setRoutePlanner(routePlanner)
		        .build();
		try {
			HttpGet httpget=new HttpGet(urlWithParameters);
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
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return "";
	}
	
	public static String FetchFromUrl(String urlWithParameters) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		
		
		try {
			HttpGet httpget=new HttpGet(urlWithParameters);
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
	
	@Deprecated
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
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return "";
		
	}
	
	
	public static String postForm(String url,Map<String,String> formMap) {
		String buffer="";
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		for(String key:formMap.keySet()) {
			BasicNameValuePair pair=new BasicNameValuePair(key, formMap.get(key));
			list.add(pair);
		}
		UrlEncodedFormEntity formentity = new UrlEncodedFormEntity(list, Consts.UTF_8);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost=new HttpPost(url);
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

}
