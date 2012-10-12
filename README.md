CsvToCouchDB
============

Purpose
-------
To upload the contents of a .csv file to a CouchDb instance.

Usage
-----
Command line usage as follows: java -jar [release].jar [http://couchdb/database] [/path/to/csv] [how many concurrent rows to upload] [how many upload threads] [true/false does the file have headers] [key1/header1] [key2/header2] [key/header etc]