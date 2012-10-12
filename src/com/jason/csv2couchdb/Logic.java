package com.jason.csv2couchdb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONObject;

import au.com.bytecode.opencsv.CSVReader;

public class Logic {
	
	private int threadNameNum = 0;
	private int maxThreads = 3;

	public boolean parseAndUpload(CsvFileProperties csvFile, CouchDbProperties couchDb, int batchSize, int threads){
		this.maxThreads = threads;
		try {
			CSVReader reader = new CSVReader (new FileReader(csvFile.getFilePathAndName()));
			int lineNum = 0;
			String [] nextLine;
			List<Map<String, String>> jsonArray = new ArrayList<Map<String, String>>();
			boolean uploadSuccess = true;
		    while ((nextLine = reader.readNext()) != null) {
				Map<String, String> jsonBlob = new HashMap<String, String>();
		        if (csvFile.getHasHeaders() && lineNum > 0){
		        	for (int i = 0; i < nextLine.length; i++){
		        		jsonBlob.put(csvFile.getHeaderNames()[i], nextLine[i]);
		        	}
		        	jsonArray.add(jsonBlob);
		        } else if (!csvFile.getHasHeaders()) {
		        	for (int i = 0; i < nextLine.length; i++){
		        		jsonBlob.put(csvFile.getHeaderNames()[i], nextLine[i]);
		        	}
		        	jsonArray.add(jsonBlob);
		        }
		        if (jsonArray.size() >= batchSize){
		        	uploadSuccess = this.upload(jsonArray, couchDb);
		        	if (!uploadSuccess){
		        		this.logError(jsonArray, csvFile, couchDb, batchSize);
		        	}
		        	jsonArray.clear();
		        }
		        lineNum++;
		    }
		    
		    if(!jsonArray.isEmpty()) {
	        	uploadSuccess = this.upload(jsonArray, couchDb);
	        	if (!uploadSuccess){
	        		this.logError(jsonArray, csvFile, couchDb, batchSize);
	        	}
		    }
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean upload(List<Map<String, String>> jsonList, CouchDbProperties couchDb){
		String threadName = "thread-"+this.threadNameNum;
		this.threadNameNum++;
		HashMap <String, Object> properties = new HashMap<String, Object>();
		List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
		list2.addAll(jsonList);
		properties.put("jsonList", list2);
		properties.put("couchDb", couchDb);
		UploadThreadResources.getInstance().setResourceByThreadName(threadName, properties);
		Thread thr = new Thread(new UploadThread());
		thr.setName(threadName);
		thr.start();
		this.waitForThreads();
		return true;
	}
	
	public void waitForThreads () {
		while (Thread.activeCount() >= this.maxThreads){}
	}
	
	public void logError(List<Map<String, String>> jsonList, CsvFileProperties csvFile, CouchDbProperties couchDb, int batchSize){
		
	}
	
}
