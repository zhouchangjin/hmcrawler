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
		InputStream stream =HtmlFetcher.getInputSteam("https://www.mysql.com/why-mysql/white-papers/mysql-whatsnew-56-zh/");
		DataInputStream s=new DataInputStream(stream);
		extractor.setSourceStream(s);
		extractor.parse();
	}

}
