package com.harmonywisdom.crawler.httputil;

public class HttpUrlUtil {
	
	public static String getMainHost(String hostUrl){
		String pureHost=hostUrl.replace("http://", "");
		pureHost=pureHost.substring(0, pureHost.indexOf("/"));
		String parts[]=pureHost.split("\\.");
		if(parts.length==2){
			return parts[0]+"."+parts[1];
		}else{
			String res="";
			for(int i=1;i<parts.length;i++){
				res+=parts[i];
				if(i!=parts.length-1){
					res+=".";
				}
			}
			return res;
		}
	}
	
	public static String getHost(String hostUrl){
		String pureHost=hostUrl.replace("http://", "");
		pureHost=pureHost.substring(0, pureHost.indexOf("/"));
		return pureHost;

	}
	
	public static void main(String args[]) {
		System.out.println(getHost("http://product.m.dangdang.com/detail23812468-0-1.html"));
		
	}

}
