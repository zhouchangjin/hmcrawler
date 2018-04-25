package com.harmonywisdom.crawler.page;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

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

public class PageSelector implements IPageSelector {
	String cont;
	Document doc;
	Class clz;

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}
	
	public Class getClz() {
		return clz;
	}

	public void setClz(Class clz) {
		this.clz = clz;
	}

	public PageSelector(String content) {
		this.cont = content;
		
	}
	
	public PageSelector() {
		
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
	public String selectByXpath(String xpath) {
		// TODO Auto-generated method stub
		Node node;
		try {
			node = XPathAPI.selectSingleNode(doc, xpath);
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
			Node node = XPathAPI.selectSingleNode(doc, path);
			return node.getAttributes().getNamedItem(attributeName).getNodeValue();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	private void process(Object obj, String prop, String dataType, String value) {
		String propName = prop.substring(0, 1).toUpperCase() + prop.substring(1);
		try {
				if (dataType.equals("String")) {
					Method m = obj.getClass().getMethod("set" + propName, String.class);
					if(m!=null) {
						m.invoke(obj, value);
					}
					
				}else if(dataType.equals("Integer")) {
					Method m = obj.getClass().getMethod("set" + propName, Integer.class);
					if(m!=null) {
						m.invoke(obj, Integer.parseInt(value));
					}
					
				}else if(dataType.equals("Double")) {
					Method m = obj.getClass().getMethod("set" + propName, Double.class);
					if(m!=null) {
						m.invoke(obj, Double.parseDouble(value));
					}
					
				}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public  Object buildObject(ObjectPageBingding binding) {
		// TODO Auto-generated method stub
		try {
			Object obj=clz.newInstance();
			Iterator<String> it = binding.propertyIterator();
			while (it.hasNext()) {
				String prop = it.next();
				String xPath = binding.getXpath(prop);
				String type = binding.getXpathType(prop);
				String dataType = binding.getDataTypeBind(prop);
				if (type.equals("attr")) {
					
					String value=selectAttribute(xPath);
					process(obj, prop, dataType, value);

				} else if (type.equals("value")) {
					String value = selectByXpath(xPath);
					process(obj, prop, dataType, value);
				}

			}
			return obj;

		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String selectAttribute(String path) {
		// TODO Auto-generated method stub
		Node node;
		try {
			node = XPathAPI.selectSingleNode(doc, path);
			return node.getNodeValue();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	

	public static void main(String args[]) {

		PageSelector parser = new PageSelector(HtmlFetcher.FetchHtml("http://www.51meiyu.cn"));
		parser.initialize();
		String tmp = parser.selectByXpath("//div[@class='phonelabel']");
		String tm2 = parser.selectAttribute("//p[@class='else_sort']/a/@href");
		
		
		System.out.println("==========" + tm2);
	}

}
