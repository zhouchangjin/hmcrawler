package com.harmonywisdom.crawler.page;

import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.harmonywisdom.crawler.httputil.HtmlFetcher;


public class DOMParser implements IPageSelector{
	String xmlCont;
	Document document;
	public DOMParser(String xml) {
		this.xmlCont=xml;
		initialize();
	}
	
	public void initialize() {
		SAXReader reader = new SAXReader();
		try {
			Document doc=reader.read(new StringReader(this.xmlCont));
			this.document=doc;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String selectByXpath(String xpath) {
		// TODO Auto-generated method stub
		Node node=this.document.selectSingleNode(xpath);
		return node.getText();
	}
	
	
	public static void main(String args[]) {
		
		JTidyTider tider=new JTidyTider();
		String res=tider.htmlToXml(HtmlFetcher.FetchHtml("http://www.baidu.com"));
		DOMParser parser=new DOMParser(res);
		//String tmp=parser.selectByXpath("//div[id='head']");
		//System.out.println(tmp);
	}

}
