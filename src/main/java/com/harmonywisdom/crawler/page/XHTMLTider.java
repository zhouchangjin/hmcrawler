package com.harmonywisdom.crawler.page;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.w3c.tidy.Tidy;

import com.harmonywisdom.crawler.httputil.HtmlFetcher;

public class XHTMLTider implements IPageTider {

	@Override
	public String htmlToXml(String input) {
		// TODO Auto-generated method stub
		ByteArrayInputStream bai=new ByteArrayInputStream(input.getBytes());
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		Tidy tidy=new Tidy();
		tidy.setXHTML(true);
		tidy.parse(bai, bos);
		String page=bos.toString();
		return page;
	}
	
	
	public static void main(String ars[]) {
		//XHTMLTider tider=new XHTMLTider();
		//System.out.println(tider.htmlToXml(HtmlFetcher.FetchHtml("http://www.baidu.com")));

	}

}
