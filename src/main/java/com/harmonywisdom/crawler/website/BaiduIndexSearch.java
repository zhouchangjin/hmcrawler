package com.harmonywisdom.crawler.website;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.harmonywisdom.crawler.httputil.HtmlFetcher;
import com.harmonywisdom.crawler.httputil.RedirectFetcher;
import com.harmonywisdom.crawler.page.Tuple;

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
	
	public static List<Tuple<String, String>> baiduIndex(String keyname,int page){
		ArrayList<Tuple<String,String>> list=new ArrayList<Tuple<String,String>>();
		String baiduPage="http://www.baidu.com/s";
		String cont=HtmlFetcher.FetchHtml(baiduPage,"wd="+keyname);
		String regexp="<a(.\\s+?)data-click(.*?)href\\s+=\\s+\"([^\"]*?)\"([^>]*?)>([^<]*?)</a>";
		Pattern p=Pattern.compile(regexp);
		Matcher m=p.matcher(cont);
		while(m.find()) {
			Tuple<String,String> t=new Tuple<String, String>(m.group(3), m.group(5));
			list.add(t);
		}
		return list;
		
		
	}
	
	public static void main(String args[]) {
//		List<Tuple<String,String>> list=baiduIndex("site:51meiyu.cn",1);
//		for(Tuple t:list) {
//			System.out.println(t.getLeft()+"===="+t.getRight());
//		}
		System.out.println(RedirectFetcher.getRedirect("http://www.baidu.com/link","url=ExgrdWNSZBtMqDsbWLd2ZGtD1-mi7CF_cLKpHmorZvO"));
	}

}
