package com.jason.csv2couchdb;

import java.util.ArrayList;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CouchDbProperties couch = new CouchDbProperties();
		couch.setServerAddress(args[0]);
		CsvFileProperties csv = new CsvFileProperties();
		csv.setFilePathAndName(args[1]);
		int uploadSize = Integer.parseInt(args[2]);
		int concurrentUploadThreads = Integer.parseInt(args[3]);
		csv.setHasHeaders(Boolean.getBoolean(args[4]));
		ArrayList<String> headearsList = new ArrayList<String>();
		for (int i = 5; i < args.length; i++){
			headearsList.add(args[i]);
		}
		String [] headers = {};
		headers = headearsList.toArray(headers);
		csv.setHeaderNames(headers);
		new Logic().parseAndUpload(csv, couch, uploadSize, concurrentUploadThreads);
	}

}
