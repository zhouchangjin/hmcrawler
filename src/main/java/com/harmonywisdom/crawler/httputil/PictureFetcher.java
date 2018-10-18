package com.harmonywisdom.crawler.httputil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class PictureFetcher {
	
	public static String FetchPicture(String url,String savefileName,String savePath){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget=new HttpGet(url);
		String chars[]=url.split("\\.");
		String suf=".jpg";
		if(chars.length>1){
			suf=chars[chars.length-1];
		}
		System.out.println("文件后缀"+suf);
		String finalFileName=savePath+"/"+savefileName+"."+suf;
		try {
			CloseableHttpResponse response=httpclient.execute(httpget);
			HttpEntity entity=response.getEntity();
			InputStream is=entity.getContent();
			byte[] outbytes=new byte[1024];
			int size=0;
			OutputStream os=new FileOutputStream(new File(finalFileName));
			while((size=is.read(outbytes))>0){
				os.write(outbytes, 0, size);
			}
			os.flush();
			os.close();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String FetchPicture(String url,String savefileName,String savePath,String suffix){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget=new HttpGet(url);
		//String chars[]=url.split("\\.");
		
		String finalFileName=savePath+"/"+savefileName+"."+suffix;
		try {
			CloseableHttpResponse response=httpclient.execute(httpget);
			HttpEntity entity=response.getEntity();
			InputStream is=entity.getContent();
			byte[] outbytes=new byte[1024];
			int size=0;
			OutputStream os=new FileOutputStream(new File(finalFileName));
			while((size=is.read(outbytes))>0){
				os.write(outbytes, 0, size);
			}
			os.flush();
			os.close();
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
