package httpclient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class HTTPClientDemo {
	
	private static String URL = "http://api.money.126.net/data/feed/1000002,1000001,money.api";

	// 测试模拟http get方法
	@Test
	public void testGetMethod() throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(URL);
		CloseableHttpResponse response = httpclient.execute(httpGet);
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally clause.
		// Please note that if response content is not fully consumed the underlying
		// connection cannot be safely re-used and will be shut down and discarded
		// by the connection manager. 
		
		System.out.println(response.getStatusLine());
		HttpEntity entity = response.getEntity();
		
		// do something useful with the response body
		// and ensure it is fully consumed
		try {
			EntityUtils.consume(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.close();
		}
	}
	
	// 测试模拟http post方法
	@Test
	public void testPost() throws ClientProtocolException, IOException{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://targethost/login");
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "vip"));
		nvps.add(new BasicNameValuePair("password", "secret"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response2 = httpclient.execute(httpPost);

		try {
		    System.out.println(response2.getStatusLine());
		    HttpEntity entity2 = response2.getEntity();
		    // do something useful with the response body
		    // and ensure it is fully consumed
		    EntityUtils.consume(entity2);
		} finally {
		    response2.close();
		}
	}
	
	// 更便捷的API
	@Test
	public void testSimpleAPI() throws ClientProtocolException, IOException {
		// The fluent API relieves the user from having to deal with manual deallocation of system
		// resources at the cost of having to buffer response content in memory in some cases.

		System.out.println(Request.Get(URL).execute().returnContent());
		Request.Post(URL)
				.bodyForm(Form.form().add("username", "vip").add("password", "secret").build()).execute()
				.returnContent();
	}
	
	@Test
	public void testTransfertToJson() throws ClientProtocolException, IOException{
		Charset charset = Charset.forName("UTF-8");
		String originalJsonContent = Request.Get(URL).execute().returnContent().asString(charset);
		String stockContent = originalJsonContent.substring(21, originalJsonContent.length()-2);
		System.out.println(stockContent);
		Map<String , NetEaseStock> stockMap = new ConcurrentHashMap<>();
		for(Object stock : JSON.parseObject(stockContent).entrySet()){
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) stock;
			stockMap.put(entry.getKey(), JSON.parseObject(entry.getValue().toString(),NetEaseStock.class));
		}
		
		System.out.println(stockMap.toString());
	}
	
	@Test
	public void testVoid(){
		System.out.println("_ntes_quote_callback(".length());
	}

}
