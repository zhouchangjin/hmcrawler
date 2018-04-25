package com.harmonywisdom.crawler.page;



import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.apache.xerces.xni.parser.XMLDocumentFilter;
import org.apache.xpath.XPathAPI;
import org.cyberneko.html.filters.Purifier;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import com.harmonywisdom.crawler.httputil.HtmlFetcher;


public class Selector implements IPageSelector{
	String cont;
	String xmlResult;
	Document doc;
	public Selector(String xml) {
		this.cont=xml;
		initialize();
	}
	
	public void initialize() {
		
		DOMParser parser=new DOMParser();
		 XMLDocumentFilter noop = new Purifier();
		 XMLDocumentFilter[] filters = { noop };
		 try {
			parser.setProperty("http://cyberneko.org/html/properties/filters", filters);
			parser.setFeature("http://xml.org/sax/features/namespaces", false);  
			 ByteArrayInputStream bio=new ByteArrayInputStream(cont.getBytes("UTF-8"));
			 InputSource se=new InputSource(bio);
			 parser.parse(se);
			 doc=parser.getDocument();
		} catch (SAXNotRecognizedException e) {
			e.printStackTrace();
		} catch (SAXNotSupportedException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		
		String tmp=parser.selectByXpath("//DIV");
		System.out.println(tmp);
	}

}
