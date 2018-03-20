package com.harmonywisdom.crawler.website;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.harmonywisdom.crawler.httputil.HtmlFetcher;

public class BaiduIndexSearch {
	
	public static int indexCnt(String url) {

		String baiduPage="http://www.baidu.com/s";
		String cont=HtmlFetcher.FetchHtml(baiduPage,"wd="+url);
		String regexp="相关结果约(.*?)个";
		Pattern p=Pattern.compile(regexp);
		Matcher m=p.matcher(cont);
		String num="0";
		while(m.find()) {
			num=m.group(1);
			num=num.replace(",", "");
			break;
		}
		
		int res=Integer.parseInt(num);
		return res;
	}
	
	public static int siteCnt(String url){
		String baiduPage="http://www.baidu.com/s";
		String cont=HtmlFetcher.FetchHtml(baiduPage,"wd=site:"+url);
		String regexp="相关结果约(.*?)个";
		Pattern p=Pattern.compile(regexp);
		Matcher m=p.matcher(cont);
		String num="0";
		while(m.find()) {
			num=m.group(1);
			num=num.replace(",", "");
			break;
		}
		
		int res=Integer.parseInt(num);
		return res;
	}
	
	public static void main(String args[]) {
		System.out.println(siteCnt("51meiyu.cn"));
	}

}
