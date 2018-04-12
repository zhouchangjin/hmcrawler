package com.harmonywisdom.crawler.website;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		String cont=HtmlFetcher.FetchHtml(baiduPage,"wd="+keyname,"pn="+(page*10));
		String regexp="<a(.\\s+?)data-click(.*?)href\\s+=\\s+\"([^\"]*?)\"([^>]*?)>([^<]*?)</a>";
		Pattern p=Pattern.compile(regexp);
		Matcher m=p.matcher(cont);
		while(m.find()) {
			Tuple<String,String> t=new Tuple<String, String>(m.group(3), m.group(5));
			list.add(t);
		}
		return list;
		
		
	}
	
	
	public static List<IndexingStatus> siteIndex(String siteDomain,int level){
		
		ArrayList<IndexingStatus> list=new ArrayList<IndexingStatus>();
		String searchWd="";
		if(level==0) {
			searchWd="site:"+siteDomain;
		}else if(level==1) {
			searchWd="site:."+siteDomain;
		}
		int cnt=indexCnt(searchWd);
		int pageN=cnt/10+1;
		for(int i=0;i<pageN;i++) {
			System.out.println("处理第"+i+"页");
			List<Tuple<String,String>> result=baiduIndex(searchWd, i);
			for(Tuple t:result) {
				String link=t.getLeft().toString();
				String id=link.split("url=")[1];
				String url=RedirectFetcher.getRedirect("http://www.baidu.com/link", "url="+id);
				IndexingStatus is=new IndexingStatus();
				is.setPage(i);
				is.setSite(siteDomain);
				is.setTime(new Date());
				is.setTitle(t.getRight().toString());
				is.setTotalCnt(cnt);
				is.setUrl(url);
				is.setType("baidu");
				list.add(is);
			}
		}
		return list;
	}
	
	public static void main(String args[]) {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat sdfFile=new SimpleDateFormat("yyyyMMdd");
			BufferedWriter bw=new BufferedWriter(new FileWriter(new File("c:/index"+sdfFile.format(new Date())+".csv")));
			List<IndexingStatus> islist=siteIndex("51meiyu.cn", 1);
			for(IndexingStatus s:islist) {
				String line=s.getUrl()+","+sdf.format(s.getTime())+","+"收录,未知,未知,未知,未知";
				System.out.println(line);
				bw.append(line);
				bw.newLine();
				
			}
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
