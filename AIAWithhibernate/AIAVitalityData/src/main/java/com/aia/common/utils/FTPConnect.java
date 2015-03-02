package com.aia.common.utils;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.aia.data.DataOutputProcessor;

public class FTPConnect {
	
	static Logger logger = Logger.getLogger(FTPConnect.class);
	private static String host="";
	private static String username="";
	private static String password="";
	static{

		Properties prop = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream("config\\ftp.properties");

			// load a properties file
			prop.load(input);

			Enumeration e = prop.propertyNames();
			host = prop.getProperty("host");
			username = prop.getProperty("username");
			password = prop.getProperty("password");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	}
	 public static FTPClient getFtpConnection()
	  {
		  // get an ftpClient object  
		  FTPClient ftpClient = new FTPClient();  
		  
		  try {  
		   // pass directory path on server to connect  
		   ftpClient.connect(host);  
		  
		   // pass username and password, returned true if authentication is  
		   // successful  
		   boolean login = ftpClient.login(username, password);  
		  
		   if (login) {  
		    System.out.println("Connection established...");  
		    System.out.println("Status: "+ftpClient.getStatus());  
		 
		   } else {  
		    System.out.println("Connection fail...");  
		   }  
		  
		  } catch (SocketException e) {  
		   e.printStackTrace();  
		  } catch (IOException e) {  
		   e.printStackTrace();  
		  }   
		  return ftpClient;
		 }  
	 
	 public static void retrieveFiles(FTPClient ftpClient, String localDirectory) {
			FTPFile[] files = null;
			OutputStream output = null;
			BufferedOutputStream bos = null;
			try {
				ftpClient.setBufferSize(1024 * 1024);
				ftpClient.enterLocalPassiveMode();
				files = ftpClient.listFiles();
				for (FTPFile file : files) {
					String details = file.getName();
					output = new FileOutputStream(localDirectory + "/"
							+ file.getName());
					ftpClient.retrieveFile(details, output);
					output.close();
					output = null;
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 
	 public static void disconnectConn(FTPClient ftpClient)
	 {
		 try
		 {
			 boolean logout = ftpClient.logout();  
			    if (logout) {  
			     System.out.println("Connection close...");  
			    }  
			   else {  
			    System.out.println("Connection fail...");  
			   }  
		 }
		 catch (SocketException e) {  
			   e.printStackTrace();  
			  } catch (IOException e) {  
			   e.printStackTrace();  
			  } finally {  
			   try {  
			    ftpClient.disconnect();  
			   } catch (IOException e) {  
			    e.printStackTrace();  
			   }  
			  }  
	 }

}
