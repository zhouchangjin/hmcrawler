package com.harmonywisdom.crawler.page;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


import org.apache.xpath.XPathAPI;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.harmonywisdom.crawler.httputil.HtmlFetcher;


public class Selector implements IPageSelector{
	String cont;
	Document doc;
	public Selector(String content) {
		this.cont=content;
		initialize();
	}
	
	public void initialize() {
		HtmlCleaner cleaner=new HtmlCleaner();
		try {
			TagNode node=cleaner.clean(new ByteArrayInputStream(cont.getBytes()));

			this.doc= new DomSerializer(new CleanerProperties()).createDOM(node);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	}

	@Override
	public String selectByXpath(String xpath) {
		// TODO Auto-generated method stub
		Node node;
		try {
			node=XPathAPI.selectSingleNode(doc, xpath);
			return node.getTextContent();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	
	public static void main(String args[]) {
		

		Selector parser=new Selector(HtmlFetcher.FetchHtml("http://www.51meiyu.cn"));
		
		String tmp=parser.selectByXpath("//div[@class='phonelabel']");
		System.out.println("=========="+tmp);
	}

}
