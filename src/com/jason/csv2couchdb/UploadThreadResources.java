package com.jason.csv2couchdb;

import java.util.HashMap;
import java.util.Map;

public class UploadThreadResources {
	private static Map<String, Map<String, Object>> resource;
	private static UploadThreadResources _instance;
	
	private UploadThreadResources(){
		resource = new HashMap<String, Map<String, Object>>();
	}
	
	public static synchronized UploadThreadResources getInstance() {
		if (_instance == null) {
			_instance = new UploadThreadResources();
		}
		return _instance;
	}
	
	public synchronized Map<String, Object> getResourcesByThreadName(String name){
		return resource.get(name);
	}
	
	public synchronized boolean setResourceByThreadName(String name, HashMap<String, Object> resourceMap){
		resource.put(name, resourceMap);
		return true;
	}
}
