package com.harmonywisdom.crawler.page;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ListPageSelector implements IListPageSelector {
	String cont;
	Document doc;
	Class clz;

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public Class getClz() {
		return clz;
	}

	public void setClz(Class clz) {
		this.clz = clz;
	}
	
	public void initialize() {
		HtmlCleaner cleaner = new HtmlCleaner();
		try {
			TagNode node = cleaner.clean(new ByteArrayInputStream(cont.getBytes()));

			this.doc = new DomSerializer(new CleanerProperties()).createDOM(node);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<IPageSelector> selectItems(String xpath) {
		// TODO Auto-generated method stub
		List<IPageSelector> list=new ArrayList<IPageSelector>();
		
		
		
		NodeList nodelist;
		try {
			nodelist = XPathAPI.selectNodeList(doc, xpath);
			for(int i=0;i<nodelist.getLength();i++) {
				Node node=nodelist.item(i);
				String cnt=W3CNodeUtil.getInnerHTML(node);
				PageSelector selector=new PageSelector();
				selector.setCont(cnt);
				selector.setClz(clz);
				selector.initialize();
				list.add(selector);
			}
			return list;
		} catch (TransformerException e) {
			e.printStackTrace();
			return list;
		}
	}
	
	public String selectContentByXpath(String xpath) {
		Node node;
		try {
			node = XPathAPI.selectSingleNode(doc, xpath);
			return W3CNodeUtil.getInnerHTML(node);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}
