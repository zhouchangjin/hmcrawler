package com.harmonywisdom.crawler.page;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

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


public class PageSelector implements IPageSelector{
	String cont;
	Document doc;
	public PageSelector(String content) {
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
	
	@Override
	public String selectAttribute(String path, String attributeName) {
		// TODO Auto-generated method stub
		try {
			Node node=XPathAPI.selectSingleNode(doc, path);
			return node.getAttributes().getNamedItem(attributeName).getNodeValue();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String args[]) {
		

		PageSelector parser=new PageSelector(HtmlFetcher.FetchHtml("http://www.51meiyu.cn"));
		
		String tmp=parser.selectByXpath("//div[@class='phonelabel']");
		String tm2=parser.selectAttribute("//p[@class='else_sort']/a", "href");
		System.out.println("=========="+tm2);
	}





}
