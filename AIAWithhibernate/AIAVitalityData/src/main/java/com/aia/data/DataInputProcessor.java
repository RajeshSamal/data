package com.aia.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.aia.common.utils.Constants;
import com.aia.common.utils.FTPConnect;
import com.aia.dao.CustomObjectDao;
import com.aia.dao.DataFileDao;
import com.aia.dao.EloquaDao;
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

public class DataInputProcessor {

	private static String localDirectory = "D://Temp";
	static Logger logger = Logger.getLogger(DataInputProcessor.class);
	public static Map<String, String> fileToClassMap = new HashMap<String, String>();
	
	public static Map<String, Map<String, String>> fileMap = new HashMap<String, Map<String, String>>();
	
	public static String[] fileTypeList= {"HK-ACHIEVE_GOLD",
											"HK-ACHIEVE_SILVER",
											"HK-ACHIEVE_PLATINUM",
											"HK-ENGAGEMENT_REMINDER1",
											"HK-ENGAGEMENT_REMINDER2",
											"HK-ENGAGEMENT_REMINDER3",
											"HK-ADIDAS_MICOACH_CONCENT_REMINDER",
											"HK-MID_YEAR_ASSESSMENT",
											"HK-DOWNGRADE_WARNING",
											"HK-APPROACH_GOLD",
											"HK-APPROACH_SILVER",
											"HK-APPROACH_PLATINUM"};
	
	public static String[] properieslist= {"HKAchieveGold.properties",
											"HKAchieveSilver.properties",
											"HKAchievePlatinum.properties",
											"HKEngagementReminder1.properties",
											"HKEngagementReminder2.properties",
											"HKEngagementReminder3.properties",
											"HKAdidasMicoachContentReminder.properties",
											"HKMidYearAssessment.properties",
											"HKDowngradeWarning.properties",
											"HKApprochGold.properties",
											"HKApprochSilver.properties",
											"HKApprochPlatinum.properties"};


	static {

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
			for(int i=0; i<fileTypeList.length;i++){
				input = loader.getResourceAsStream(properieslist[i]);
				prop.load(input);
				e = prop.propertyNames();
				Map<String, String> columnMap = new HashMap<String, String>();

				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					String value = prop.getProperty(key);
					columnMap.put(key, value);
				}

				fileMap.put(fileTypeList[i], columnMap);
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
	
	public static Object getDao(String daoName){
		
		if(daoName != null && daoName.equals(Constants.HK_GOLD_ARCHIVE)){
			return new HkagDAO();
		}
		
		else if(daoName != null && daoName.equals(Constants.HK_SILVER_ARCHIVE)){
			return new HkasDAO();
		}
		else if(daoName != null && daoName.equals(Constants.HK_PLATINUM_ARCHIVE)){
			return new HkapDAO();
		}
		else if(daoName != null && daoName.equals(Constants.HK_ENGAGEMENT_REMINDER1)){
			return new Hker1DAO();
		}
		else if(daoName != null && daoName.equals(Constants.HK_ENGAGEMENT_REMINDER2)){
			return new Hker2DAO();
		}
		else if(daoName != null && daoName.equals(Constants.HK_ENGAGEMENT_REMINDER3)){
			return new Hker3DAO();
		}
		else if(daoName != null && daoName.equals(Constants.HK_ADIDAS_MICOACH_CONCENT_REMINDER)){
			return new HkamcrDAO();
		}
		else if(daoName != null && daoName.equals(Constants.HK_DOWNGRADE_WARNING)){
			return new HkdwDAO();
		}
		else if(daoName != null && daoName.equals(Constants.HK_MID_YEAR_ASSESSMENT)){
			return new HkmyaDAO();
		}
		else if(daoName != null && daoName.equals(Constants.HK_APPROACH_GOLD)){
			return new HkapgDAO();
		}
		else if(daoName != null && daoName.equals(Constants.HK_APPROACH_SILVER)){
			return new HkapsDAO();
		}
		else if(daoName != null && daoName.equals(Constants.HK_APPROACH_PLATINUM)){
			return new HkappDAO();
		}
		else if(daoName != null && daoName.equals(Constants.DATAFILE)){
			return new DataFileDao();
		}
		else if(daoName != null && daoName.equals(Constants.FTP)){
			return new FTPDao();
		}
		else if(daoName != null && daoName.equals(Constants.CUSTOM)){
			return new CustomObjectDao();
		}
		return null;
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
