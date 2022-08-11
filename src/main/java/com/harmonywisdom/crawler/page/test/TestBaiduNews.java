package com.harmonywisdom.crawler.page.test;

import java.io.DataInputStream;
import java.io.InputStream;


import com.harmonywisdom.crawler.httputil.HtmlFetcher;
import com.harmonywisdom.crawler.page.HTMLNewsContentExtractor;

public class TestBaiduNews {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HTMLNewsContentExtractor extractor=new HTMLNewsContentExtractor();
		InputStream stream =HtmlFetcher.getInputSteam("http://www.ffsky.com/Article/bc7368_a15116.aspx");
		DataInputStream s=new DataInputStream(stream);
		extractor.setSourceStream(s);
		extractor.parse();
	}

}
