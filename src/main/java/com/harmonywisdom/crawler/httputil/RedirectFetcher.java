package com.harmonywisdom.crawler.httputil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RedirectFetcher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	@Deprecated
	public static String getRedirect(String url, String... params) {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		try {
			URIBuilder builder = new URIBuilder().setPath(url);
			for (String param : params) {
				String pair[] = param.split("=");
				if (pair.length == 2) {
					builder.addParameter(pair[0], pair[1]);
				} else {
					System.out.println(param + "格式有问题");
					return "";
				}

			}
			URI uri = builder.build();
			HttpGet httpget = new HttpGet(uri);
			CloseableHttpResponse response = httpclient.execute(httpget);
			Header h = response.getFirstHeader("Location");
			String location = h.getValue();
			return location;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
