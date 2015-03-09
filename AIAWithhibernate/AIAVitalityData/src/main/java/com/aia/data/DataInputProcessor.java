package com.aia.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.aia.common.utils.FTPConnect;
import com.aia.dao.CustomObjectDao;
import com.aia.dao.DataFileDao;
import com.aia.dao.EloquaDao;
import com.aia.dao.FTPDao;
import com.aia.dao.HkagDAO;

public class DataInputProcessor {

	private static String localDirectory = "D://Temp";
	static Logger logger = Logger.getLogger(DataInputProcessor.class);
	public static Map<String, String> fileToClassMap = new HashMap<String, String>();
	private static Map<String, String> HKAchieveGoldColumnMap = new HashMap<String, String>();
	public static Map<String, Map<String, String>> fileMap = new HashMap<String, Map<String, String>>();
	public static HkagDAO hkagDAO;
	public static DataFileDao fileDAO;
	public static EloquaDao eloquaDao;
	public static FTPDao ftpDao;
	public static CustomObjectDao customDao;
	public static String[] fileTypeList= {"HK-ACHIEVE_GOLD"};

	static {
		hkagDAO = new HkagDAO();
		fileDAO = new DataFileDao();
		eloquaDao = new EloquaDao();
		ftpDao = new FTPDao();
		customDao = new CustomObjectDao();
		Properties prop = new Properties();
		InputStream input = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("filetoclass.properties");

			prop.load(input);

			Enumeration e = prop.propertyNames();

			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				fileToClassMap.put(key, value);
			}
			input = loader.getResourceAsStream("HKAchieveGold.properties");
			prop.load(input);
			e = prop.propertyNames();

			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				HKAchieveGoldColumnMap.put(key, value);
			}

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
	static {
		fileMap.put("HK-ACHIEVE_GOLD", HKAchieveGoldColumnMap);
	}
	

	public static void startProcessing() {
		//FTPConnect.processFTP(localDirectory);
		FileToObjectList.processAllFilesToDB(localDirectory);
		for (String key:fileTypeList){
			DataOutputProcessor.sendToElqua(key);
			DataOutputProcessor.sendDistinctDuplicateToElqua(key);
		}
		

	}

	public static void main(String[] args) throws IOException {

		if (args.length > 0) {
			localDirectory = args[0];

		}
		startProcessing();
	}

}
