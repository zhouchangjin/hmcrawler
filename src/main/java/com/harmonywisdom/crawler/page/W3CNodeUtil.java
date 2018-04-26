package com.harmonywisdom.crawler.page;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

import net.sf.saxon.TransformerFactoryImpl;

public class W3CNodeUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String getInnerHTML(Node node) throws TransformerConfigurationException, TransformerException
	{
	    StringWriter sw = new StringWriter();
	    Result result = new StreamResult(sw);
	    TransformerFactory factory = new TransformerFactoryImpl();
	    Transformer proc = factory.newTransformer();
	    proc.setOutputProperty(OutputKeys.METHOD, "html");
	    for (int i = 0; i < node.getChildNodes().getLength(); i++)
	    {
	        proc.transform(new DOMSource(node.getChildNodes().item(i)), result);
	    }
	    return sw.toString();
	}

}
