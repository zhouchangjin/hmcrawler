package com.harmonywisdom.crawler.page.test;

import java.io.DataInputStream;
import java.io.InputStream;

import javax.swing.text.html.HTML;

import com.harmonywisdom.crawler.httputil.HtmlFetcher;
import com.harmonywisdom.crawler.page.HTMLNewsContentExtractor;

public class TestBaiduNews {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HTMLNewsContentExtractor extractor=new HTMLNewsContentExtractor();
		InputStream stream =HtmlFetcher.getInputSteam("http://beijing.qianlong.com/2017/0309/1478474.shtml");
		DataInputStream s=new DataInputStream(stream);
		extractor.setSourceStream(s);
		extractor.parse();
	}

}
