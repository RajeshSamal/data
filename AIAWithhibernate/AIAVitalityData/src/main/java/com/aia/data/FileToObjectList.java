package com.aia.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.CSVReader;
import com.aia.common.utils.Constants;
import com.aia.common.utils.FTPConnect;
import com.aia.dao.DataFileDao;
import com.aia.dao.DbConnectionFactory;
import com.aia.dao.FTPDao;
import com.aia.dao.HkagDAO;
import com.aia.dao.HkamcrDAO;
import com.aia.dao.HkapDAO;
import com.aia.dao.HkapgDAO;
import com.aia.dao.HkappDAO;
import com.aia.dao.HkapsDAO;
import com.aia.dao.HkasDAO;
import com.aia.dao.HkdwDAO;
import com.aia.dao.Hker1DAO;
import com.aia.dao.Hker2DAO;
import com.aia.dao.Hker3DAO;
import com.aia.dao.HkmyaDAO;
import com.aia.model.DataFile;
import com.aia.model.HKAchieveGold;

public class FileToObjectList {

	//input:HK-ACHIEVE_GOLD-554335.csv-output: HK-ACHIEVE_GOLD
	private static String getFileNameFromClassName(String fileName) {
		String classMapName ="";
		try{
			int lastIndexOfHypen = fileName.lastIndexOf("-");
			int lastIndexOfSlash = fileName.lastIndexOf("\\");
			classMapName = fileName.substring(lastIndexOfSlash + 1,
					lastIndexOfHypen);
			return classMapName;

		}
		catch(Exception e){
			return classMapName;
		}
				
	}
	

	private static boolean isFileValid(String fileName){
		boolean value=false;
		String fileTypeIncoming =getFileNameFromClassName(fileName);
		for(String Filetype: DataInputProcessor.fileTypeList){
			if(fileTypeIncoming!=null && fileTypeIncoming.equals(Filetype)){
				value=true;
			}
		}
		return value;
	}

	//input:HK-ACHIEVE_GOLD-554335.csv-output Class Object
	public static Class getFileClass(String fileName) {

		Class fileClass = null;
		String classMapName = getFileNameFromClassName(fileName);
		String className = DataInputProcessor.fileToClassMap.get(classMapName);
		try {
			fileClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileClass;

	}
	//input : HK-ACHIEVE_GOLD-output Class Object
	public static Class getClassFromFile(String fileName) {

		Class fileClass = null;
		String className = DataInputProcessor.fileToClassMap.get(fileName);
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
		Map<String, String> classColumnMap = DataInputProcessor.fileMap.get(mapName);
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
			fr.close();
			csvr.close();
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

	private static int[] processFile(String fileNameWithDir, Session session, String fileName) {
		Class fileClass = getFileClass(fileNameWithDir);
		List recordObjectList = fileToRecordList(fileNameWithDir, fileClass);
		List safeToSave = HandleDuplicates.handleDuplicate(recordObjectList,fileClass);
		saveToDatBase(safeToSave, fileClass, session,fileName);
		int[] records = new int[2];
		records[0] = recordObjectList.size();
		records[1] = HandleDuplicates.noOdDuplicate;
		return records;

	}
	
	private static void saveToDatBase(List objectList, Class fileClass,Session session,String fileName) {
		
		if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveGold")) {
			((HkagDAO)(DataInputProcessor.getDao(Constants.HK_GOLD_ARCHIVE))).insertList(session, objectList,fileName);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchievePlatinum")) {
			((HkapDAO)(DataInputProcessor.getDao(Constants.HK_PLATINUM_ARCHIVE))).insertList(session, objectList,fileName);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveSilver")) {
			((HkasDAO)(DataInputProcessor.getDao(Constants.HK_SILVER_ARCHIVE))).insertList(session, objectList,fileName);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder1")) {
			((Hker1DAO)(DataInputProcessor.getDao(Constants.HK_ENGAGEMENT_REMINDER1))).insertList(session, objectList,fileName);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder2")) {
			((Hker2DAO)(DataInputProcessor.getDao(Constants.HK_ENGAGEMENT_REMINDER2))).insertList(session, objectList,fileName);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder3")) {
			((Hker3DAO)(DataInputProcessor.getDao(Constants.HK_ENGAGEMENT_REMINDER3))).insertList(session, objectList,fileName);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAdidasMicConRem")) {
			((HkamcrDAO)(DataInputProcessor.getDao(Constants.HK_ADIDAS_MICOACH_CONCENT_REMINDER))).insertList(session, objectList,fileName);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKMidYearAssessment")) {
			((HkmyaDAO)(DataInputProcessor.getDao(Constants.HK_MID_YEAR_ASSESSMENT))).insertList(session, objectList,fileName);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKDowngradeWarning")) {
			((HkdwDAO)(DataInputProcessor.getDao(Constants.HK_DOWNGRADE_WARNING))).insertList(session, objectList,fileName);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKApproachGold")) {
			((HkapgDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_GOLD))).insertList(session, objectList,fileName);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKApproachSilver")) {
			((HkapsDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_SILVER))).insertList(session, objectList,fileName);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKApproachPlatinum")) {
			((HkappDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_PLATINUM))).insertList(session, objectList,fileName);
		}

	}

	public static void processAllFilesToDB(String localDirectory) {
		Session session = null;
		Transaction tx = null;
		DataFile dataFile = null;
		SessionFactory sqlSessionFactory = DbConnectionFactory.getSessionFactory();
		File folder = new File(localDirectory);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			try {
				if (isFileValid(listOfFiles[i].getName()) && listOfFiles[i].isFile() && (!(((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).isFileProcessed(listOfFiles[i].getName())))) {
					session = sqlSessionFactory.openSession();
					tx = session.beginTransaction();
					int[] records =processFile(
							localDirectory + "\\" + listOfFiles[i].getName(),
							session, listOfFiles[i].getName());
					dataFile= new DataFile();
					dataFile.setFielName(listOfFiles[i].getName());
					dataFile.setProcessDate(new Date());
					dataFile.setTotalRecords(records[0]);
					dataFile.setDuplicateRecords(records[1]);
					((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).insert(dataFile,session);
					tx.commit();
					FTPConnect.moveToBackUp(localDirectory,
							listOfFiles[i].getName());
					
					
				}
			} catch (Exception e) {
				tx.rollback();
			} finally {
				if (listOfFiles[i].isFile() && session!=null && session.isOpen()) {
					session.close();
				}

			}

		}
	}

}
