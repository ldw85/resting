 /*
 * Copyright (C) 2010 Google Code.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.resting.rest.client;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.resting.component.Verb;
import com.google.resting.component.impl.ServiceResponse;
import com.google.resting.rest.CustomSSLSocketFactory;
import com.google.resting.rest.util.oauth.RequestConstants;


/**
 * Centralized utility for all REST operations. 
 * 
 * @author sujata.de
 * @since resting 0.1
 */

public class RESTClient {
	
	/**
	 * Executes REST request for HTTP
	 * 
	 * @param Domain of the REST endpoint
	 * @param path Path of the URI
	 * @param port port number of the REST endpoint
	 * @param verb Type of HTTP method(GET/POST/PUT/DELETE)
	 * 
	 * @return ServiceResponse object containing http status code and entire response as a String
	 */

	public static ServiceResponse invoke(String targetDomain, String path, Verb verb, int port, List<NameValuePair> inputParams, HttpEntity httpEntity) {
		HttpResponse response = null;
		ServiceResponse serviceResponse = null;
		String functionName="invoke";

		HttpHost targetHost = new HttpHost(targetDomain, port, RequestConstants.HTTP);
		
		HttpRequest request = buildHttpRequest(verb,path, httpEntity);
		
		HttpClient httpClient = new DefaultHttpClient();

	//	System.out.println( "Target domain: " + targetDomain);
	//	System.out.println( "Port: " + port);
	//	System.out.println( "Request path: " + path);
		// Make sure the server knows what kind of a response we will accept
		request.addHeader("Accept", "text/xml");

		try {
			// execute is a blocking call, it's best to call this code in a
			// thread separate from the ui's
			final long startTime = System.currentTimeMillis();
			response = httpClient.execute(targetHost, request);
			final long endTime = System.currentTimeMillis();

			serviceResponse = new ServiceResponse(response);
			
			final long endTime2 = System.currentTimeMillis();
			
		//	System.out.println( "The REST response is:\n " + serviceResponse);
			System.out.println( "Time taken in REST operation : "+ (endTime - startTime) + " ms.");
			System.out.println( "Time taken in service response construction : "+ (endTime2 - endTime) + " ms.");

		}// try
		catch(ConnectTimeoutException e){
			System.out.println( "["+functionName+"] Connection timed out. The host may be unreachable.");
			e.printStackTrace();

		}catch (Exception ex) {
			ex.printStackTrace();


		} finally {

			httpClient.getConnectionManager().shutdown();

		}//try
		return serviceResponse;
	}// invoke
	
	private static HttpRequest buildHttpRequest(Verb verb, String path,  HttpEntity httpEntity) {
		
		if (verb == Verb.GET) {
			HttpGet httpGet = new HttpGet(path);
			return httpGet;
			
		} else if (verb == Verb.POST) {
			HttpPost httpPost = new HttpPost(path);
			if(httpEntity!=null)
				httpPost.setEntity(httpEntity);
			return httpPost;

		} else if (verb == Verb.DELETE) {
			HttpDelete httpDelete = new HttpDelete(path);
			return httpDelete;

		} else {
			HttpPut httpPut = new HttpPut(path);
			if(httpEntity!=null)
				httpPut.setEntity(httpEntity);
			return httpPut;
		}//if
	}//buildHttpRequest
	
	/**
	 * Executes secure SSL request using HTTPS
	 * 
	 * @param Domain of the REST endpoint
	 * @param path Path of the URI
	 * @param port port number of the REST endpoint
	 * @param verb Type of HTTP method(GET/POST/PUT/DELETE)
	 * 
	 * @return ServiceResponse object containing http status code and entire response as a String
	 */
	public static ServiceResponse secureInvoke(String targetDomain, String path, Verb verb, int port, List<NameValuePair> inputParams, HttpEntity httpEntity){
		ServiceResponse serviceResponse=null;
		
	//	System.out.println( "Target domain: " + targetDomain);
	//	System.out.println( "Port: " + port);
	//	System.out.println( "Request path: " + path);

		try {
			long ioStartTime=System.currentTimeMillis();
			HttpHost targetHost = new HttpHost(targetDomain, port, RequestConstants.HTTPS);
			HttpRequest request = buildHttpRequest(verb,path,httpEntity);
					
	        DefaultHttpClient httpclient = new DefaultHttpClient();
	        httpclient.getConnectionManager().getSchemeRegistry().register(new Scheme(RequestConstants.HTTPS, new CustomSSLSocketFactory(), port));

	        HttpResponse response = httpclient.execute(targetHost,request);
	        serviceResponse=new ServiceResponse(response);
		    long ioEndTime=System.currentTimeMillis();
		    
		   // System.out.println( "The REST response is:\n "+ serviceResponse);
		   // System.out.println( "Time taken in executing REST: "+(ioEndTime-ioStartTime));
		    
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return serviceResponse;

	}	//secureInvoke

}//RESTClient