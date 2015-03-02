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
import com.aia.dao.DbConnectionFactory;
import com.aia.dao.HkagDAO;
import com.aia.model.CDODetails;
import com.aia.model.CommonModel;
import com.aia.model.HKAchieveGold;
import com.aia.service.AIAService;

public class DataInputProcessor {

	private static String localDirectory = "D://Temp";
	static Logger logger = Logger.getLogger(DataInputProcessor.class);
	private static Map<String, String> fileToClassMap = new HashMap<String, String>();
	private static Map<String, String> HKAchieveGoldColumnMap = new HashMap<String, String>();
	private static Map<String, Map<String, String>> fileMap = new HashMap<String, Map<String, String>>();
	private static HkagDAO hkagDAO;
	private static SessionFactory sqlSessionFactory;

	static {
		hkagDAO = new HkagDAO();
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

	

	private static String getFileNameFromClassName(String fileName) {
		int lastIndexOfHypen = fileName.lastIndexOf("-");
		int lastIndexOfSlash = fileName.lastIndexOf("\\");
		String classMapName = fileName.substring(lastIndexOfSlash + 1,
				lastIndexOfHypen);
		return classMapName;
	}

	private static Class getFileClass(String fileName) {

		Class fileClass = null;
		String classMapName = getFileNameFromClassName(fileName);
		String className = fileToClassMap.get(classMapName);
		try {
			fileClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileClass;

	}

	private static Object createInst(Class fileClass) {
		Object model = null;
		try {
			model = Class.forName(fileClass.getName()).getConstructor()
					.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	private static Object callMethod(Object model, String fieldName,
			String columnval, Class fileClass) {
		try {
			Method method = fileClass.getMethod("set" + fieldName,
					new Class[] { columnval.getClass() });
			method.invoke(model, columnval);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}

	private static List fileToRecordList(String fileName, Class fileClass) {
		String mapName = getFileNameFromClassName(fileName);
		Map<String, String> classColumnMap = fileMap.get(mapName);
		File inputCsvbook = new File(fileName);
		FileReader fr = null;
		List objectList = new ArrayList();
		try {
			fr = new FileReader(inputCsvbook);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		CSVReader csvr = new CSVReader(fr);
		List recordList = null;
		Iterator itr = null;
		String[] headerRow = null;

		try {
			recordList = csvr.readAll();
			itr = recordList.iterator();
			if (itr.hasNext()) {
				headerRow = (String[]) itr.next();
			}
			String[] row = null;
			while (itr.hasNext()) {
				Object model = null;
				model = createInst(fileClass);
				Integer colNum = 0;

				row = (String[]) itr.next();
				for (String columnval : row) {
					String fieldName = classColumnMap.get(headerRow[colNum]);
					model = callMethod(model, fieldName, columnval, fileClass);
					colNum++;

				}
				objectList.add(model);

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return objectList;
	}

	

	private static CDODetails processHKAG(CommonModel model) {

		CDODetails cdoData = new CDODetails();

		cdoData.setAiaVitalityMemberNumber(model.getAiaVitalityMemberNumber());
		cdoData.setClientId(model.getClientId());
		cdoData.setEmailAddress(model.getEmailAddress());
		cdoData.setGender(model.getGender());
		cdoData.setEntityReferenceNumber(model.getEntityReferenceNumber());
		cdoData.setLanguagePreference(model.getLanguagePreference());
		cdoData.setMemberFirstName(model.getMemberFirstNames());
		cdoData.setMemberSurname(model.getMemberSurname());
		cdoData.setPointBalance(model.getPointsBalance());
		cdoData.setVitalityStatus(model.getVitalityStatus());
		return cdoData;

	}

	private static void processFiles(String fileName,Session session) {
		Class fileClass = getFileClass(fileName);
		List recordObjectList = fileToRecordList(fileName, fileClass);
		saveToDatBase(recordObjectList, fileClass,session);

	}

	private static void clearLocalDirectory() {
		File folder = new File(localDirectory);
		for (File file : folder.listFiles()) {
			file.delete();
		}

	}
	private static void createBackUpDir() {
		File folder = new File(localDirectory+"\\Backup");
		if (!folder.exists()) {
			folder.mkdirs();
		}

	}
	private static void moveToBackUp(String filedir, String filePath) {
		File file =new File(filedir  +"\\" +filePath);
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();             
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = format1.format(date);
		File folder = new File(localDirectory+"\\Backup"+"\\"+date1);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		file.renameTo(new File(filedir+"\\Backup"+ "\\" +date1 +"\\"+ file.getName()));
	}

	private static void saveToDatBase(List objectList, Class fileClass,Session session) {
		HKAchieveGold HKAG = null;
		
		if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveGold")) {
				hkagDAO.insertList(session, objectList);
		}

	}

	public static void main(String[] args) throws IOException {
		FTPClient ftpClient = null;
		Session session = null;
		Transaction tx = null;
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
		if (args.length > 0) {
			localDirectory = args[0];
			clearLocalDirectory();
			createBackUpDir();
			ftpClient = FTPConnect.getFtpConnection();
			FTPConnect.retrieveFiles(ftpClient,localDirectory);
			 FTPConnect.disconnectConn(ftpClient);
		}
		
		File folder = new File(localDirectory);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			try{
				if (listOfFiles[i].isFile()) {
					session = sqlSessionFactory.openSession();
					tx = session.beginTransaction();
					processFiles(localDirectory + "\\" + listOfFiles[i].getName(),session);
					tx.commit();
					moveToBackUp(localDirectory , listOfFiles[i].getName());
					//DataOutputProcessor.sendToElqua(recordObjectList, fileClass);
				}
			}
			catch(Exception e){
				tx.rollback();
			}
			finally {
				if (listOfFiles[i].isFile()){
					session.close();
				}
				

			}
			
		}
		
	}

}
