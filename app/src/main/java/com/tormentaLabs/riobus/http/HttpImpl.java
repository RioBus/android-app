package com.tormentaLabs.riobus.http;

import android.util.Log;

import com.tormentaLabs.riobus.exception.HttpException;
import com.tormentaLabs.riobus.util.Util;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public final class HttpImpl {

	private static final int HTTP_OK = 200;
	private static HttpImpl instance = new HttpImpl();

	private HttpImpl() {
	}

	public static HttpImpl getInstance() {
		return instance;
	}

	
	public BufferedReader executaChamado(final HttpResponse response) throws HttpException {
		try {
			// Execute HTTP Post Request
			int status = response.getStatusLine().getStatusCode();
			if(status == HTTP_OK) {
				final BufferedReader reader = new BufferedReader(new InputStreamReader(
						response.getEntity().getContent()));
				return reader;
			} else {
				throw new HttpException(status);
			}
		} catch (ClientProtocolException e) {
			Log.e(Util.TAG, "ClientProtocolException  -" + e.toString());
		} catch (IOException e) {
			Log.e(Util.TAG, "IOException  -" + e.toString());
		}
		return null;
	}
	
	public BufferedReader executaChamadoPostURL(final String url, final List<NameValuePair> nameValue) 
			throws HttpException {

		final HttpPost post = new HttpPost(url);
		// Create a new HttpClient and Post Header
		final HttpClient httpclient = new DefaultHttpClient();
		try {
			if(nameValue != null) {
				post.setEntity(new UrlEncodedFormEntity(nameValue));
			}
			Header[] headers = post.getAllHeaders();
			// Execute HTTP Post Request
			final HttpResponse response = httpclient.execute(post);
			return executaChamado(response);
		} catch (ClientProtocolException e) {
			Log.e(Util.TAG, "ClientProtocolException  -" + e.toString());
		} catch (IOException e) {
			Log.e(Util.TAG, "IOException  -" + e.toString());
		}
		
		return null;

	}
	
	/**
	 * Executa uma chamada get.
	 * @param url 
	 * @param nameValue
	 * @throws HttpException 
	 */
	public BufferedReader executaChamadoGetURL(String url, final List<NameValuePair> nameValue) throws HttpException {
		
		String paramString = URLEncodedUtils.format(nameValue, "utf-8");
		url += paramString;
		
		final HttpGet get = new HttpGet(url);
		
		// Create a new HttpClient and Post Header
		final HttpClient httpclient = new DefaultHttpClient();
		
		try {
			// Execute HTTP Post Request
			final HttpResponse response = httpclient.execute(get);
			return executaChamado(response);
		} catch (ClientProtocolException e) {
			Log.e(Util.TAG, "ClientProtocolException  -" + e.toString());
		} catch (IOException e) {
			Log.e(Util.TAG, "IOException  -" + e.toString());
		}
		return null;
	}
	

}