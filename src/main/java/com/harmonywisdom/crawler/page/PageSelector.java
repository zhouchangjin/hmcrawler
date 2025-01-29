package com.harmonywisdom.crawler.page;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.harmonywisdom.crawler.httputil.HtmlFetcher;

public class PageSelector implements IPageSelector {
	String cont;
	Document doc;
	Class clz;
	IInitializer initializer=null;
	
	public void setDocument(Document doc) {
		this.doc=doc;
	}

	public IInitializer getInitializer() {
		return initializer;
	}

	public void setInitializer(IInitializer initializer) {
		this.initializer = initializer;
	}

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
	public String selectByXpath(String xpath, Node context) {
		Node node;
		try {
			node = XPathAPI.selectSingleNode(context, xpath);
			if(node==null) {
				return "";
			}else {
				return node.getTextContent();
			}
			
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public String selectByXpath(String xpath) {
		// TODO Auto-generated method stub
		Node node;
		try {
			node = XPathAPI.selectSingleNode(doc, xpath);
			if(node==null) {
				return "";
			}else {
				return node.getTextContent();
			}
			
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


	private void process(Object obj, String prop, String dataType, Object val) {	
		try {
			CrawlerBeanUtil.SetPropValue(prop, val, dataType, obj);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Override
	public  Object buildObject(ObjectPageBingding binding) {
		// TODO Auto-generated method stub
		//System.out.println(cont);
		try {
			Object obj=clz.newInstance();
			if(this.initializer!=null) {
				this.initializer.initialize(obj);
			}
			Iterator<String> it = binding.propertyIterator();
			while (it.hasNext()) {
				String prop = it.next();
				String xPath = binding.getXpath(prop);
				String type = binding.getXpathType(prop);
				String dataType = binding.getDataTypeBind(prop);
				if(!dataType.startsWith("List")) {
					if (type.equals("attr")) {
						String value=selectAttribute(xPath);
						process(obj, prop, dataType, value);
					} else if (type.equals("value")) {
						String value = selectByXpath(xPath);
						process(obj, prop, dataType, value);
					}else if(type.equals("html")) {
						String value = selectContentByXpath(xPath);
						process(obj, prop, dataType, value);
					}else if(type.equals("reg")) {
						String value=selectRegExp(xPath);
						process(obj, prop, dataType, value);
					} else if(type.equals("before-after")) {
						String params[]=xPath.split("-");
						if(params.length==2) {
							String before=params[0];
							String after=params[1];
							before=before.replace("{any}", "[\\s\\S]*?");
							after=after.replace("{any}", "[\\s\\S]*?");
							String reg=before+"([\\s\\S]*?)"+after;
							String value=selectRegExp(reg);
							process(obj, prop, dataType, value);
						}
					}
				}else {
					if(type.equals("attr")) {
						List list=selectAttributes(xPath);
						process(obj,prop,dataType,list);
					}
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
	public String selectContentByXpath(String xpath, Node context) {
		Node node;
		try {
			node = XPathAPI.selectSingleNode(context, xpath);
			if(node==null) {
				return "";
			}
			return W3CNodeUtil.getInnerHTML(node);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "";
		}
	}

	public String selectContentByXpath(String xpath) {
		Node node;
		try {
			node = XPathAPI.selectSingleNode(doc, xpath);
			//System.out.println(cont);
			//System.out.println(xpath);
			if(node==null) {
				return "";
			}
			return W3CNodeUtil.getInnerHTML(node);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	public List<String> selectAttributes(String path, Node context) {
		List<String> list=new ArrayList<String>();
		NodeList nodelist;
		try {
			nodelist = XPathAPI.selectNodeList(context, path);
			if(nodelist==null) {
				return list;
			}else {
				for(int i=0;i<nodelist.getLength();i++) {
					Node node=nodelist.item(i);
					list.add(node.getNodeValue());
				}
			}
			return list;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return list;
		}
	}
	
	public List<String> selectAttributes(String path){
		List<String> list=new ArrayList<String>();
		NodeList nodelist;
		try {
			nodelist = XPathAPI.selectNodeList(doc, path);
			if(nodelist==null) {
				return list;
			}else {
				for(int i=0;i<nodelist.getLength();i++) {
					Node node=nodelist.item(i);
					list.add(node.getNodeValue());
				}
			}
			return list;
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return list;
		}
	}
	
	@Override
	public String selectAttribute(String path, Node context) {
		Node node;
		try {
			node = XPathAPI.selectSingleNode(context, path);
			if(node==null) {
				return "";
			}
			return node.getNodeValue();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
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
			if(node==null) {
				return "";
			}
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
		//String tmp = parser.selectByXpath("//div[@class='phonelabel']");
		String tm2 = parser.selectAttribute("//p[@class='else_sort']/a/@href");
		
		
		System.out.println("==========" + tm2);
	}
	
	@Override
	public String selectRegExp(String reg, Node context) {
		String content="";
		try {
			content = W3CNodeUtil.getInnerHTML(context);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pattern pat = Pattern.compile(reg);  
	    Matcher mat = pat.matcher(content);  
	    if(mat.find()){
	       return mat.group(1);
	    }
		return null;
	}

	@Override
	public String selectRegExp(String reg) {
		Pattern pat = Pattern.compile(reg);  
	    Matcher mat = pat.matcher(cont);  
	    if(mat.find()){
	       return mat.group(1);
	    }
		return null;
	}

	public Object buildObject(ObjectPageBingding binding, Node node) {
		try {
			Object obj=clz.newInstance();
			if(this.initializer!=null) {
				this.initializer.initialize(obj);
			}
			Iterator<String> it = binding.propertyIterator();
			while (it.hasNext()) {
				String prop = it.next();
				String xPath = binding.getXpath(prop);
				String type = binding.getXpathType(prop);
				String dataType = binding.getDataTypeBind(prop);
				if(!dataType.startsWith("List")) {
					if (type.equals("attr")) {
						String value=selectAttribute(xPath,node);
						process(obj, prop, dataType, value);
					} else if (type.equals("value")) {
						String value = selectByXpath(xPath,node);
						process(obj, prop, dataType, value.trim());
					}else if(type.equals("html")) {
						String value = selectContentByXpath(xPath,node);
						process(obj, prop, dataType, value);
					}else if(type.equals("reg")) {
						String value=selectRegExp(xPath,node);
						process(obj, prop, dataType, value);
					} else if(type.equals("before-after")) {
						String params[]=xPath.split("-");
						if(params.length==2) {
							String before=params[0];
							String after=params[1];
							before=before.replace("{any}", "[\\s\\S]*?");
							after=after.replace("{any}", "[\\s\\S]*?");
							String reg=before+"([\\s\\S]*?)"+after;
							String value=selectRegExp(reg,node);
							process(obj, prop, dataType, value);
						}
					}
				}else {
					if(type.equals("attr")) {
						List list=selectAttributes(xPath,node);
						process(obj,prop,dataType,list);
					}
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

}
