package com.aia.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.CSVReader;
import com.aia.common.utils.FTPConnect;
import com.aia.dao.DataFileDao;
import com.aia.dao.DbConnectionFactory;
import com.aia.dao.HkagDAO;
import com.aia.model.CDODetails;
import com.aia.model.CommonModel;
import com.aia.model.HKAchieveGold;
import com.aia.service.AIAService;

public class DataInputProcessor {

	private static String localDirectory = "D://Temp";
	static Logger logger = Logger.getLogger(DataInputProcessor.class);
	public static Map<String, String> fileToClassMap = new HashMap<String, String>();
	private static Map<String, String> HKAchieveGoldColumnMap = new HashMap<String, String>();
	public static Map<String, Map<String, String>> fileMap = new HashMap<String, Map<String, String>>();
	public static HkagDAO hkagDAO;
	public static DataFileDao fileDAO;
	

	static {
		hkagDAO = new HkagDAO();
		fileDAO = new DataFileDao();
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

	public static void main(String[] args) throws IOException {
		FTPClient ftpClient = null;

		if (args.length > 0) {
			localDirectory = args[0];
			FTPConnect.processFTP(localDirectory);

		}
		FileToObjectList.processAllFilesToDB(localDirectory);

	}

}
