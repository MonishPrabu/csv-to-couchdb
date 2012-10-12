package com.jason.csv2couchdb;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class UploadThread implements Runnable {
	
	
	public void Upload (List<Map<String, String>> jsonList, CouchDbProperties couchDb){
		 try {
			 
				URL url = new URL(couchDb.getServerAddress()+"/_bulk_docs");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				
				JSONObject obj = new JSONObject();
				obj.put("docs", jsonList);
				String input = obj.toJSONString();
		 
				OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();
		 
				if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
				}
		 
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
		 
				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
		 
				conn.disconnect();
		 
			  } catch (MalformedURLException e) {
		 
				e.printStackTrace();
		 
			  } catch (IOException e) {
		 
				e.printStackTrace();
		 
			 }
	}

	@Override
	public void run() {
		Map<String, Object> resource = UploadThreadResources.getInstance().getResourcesByThreadName(Thread.currentThread().getName());
		this.Upload((List<Map<String, String>>)resource.get("jsonList"), (CouchDbProperties)resource.get("couchDb"));
	}
}
