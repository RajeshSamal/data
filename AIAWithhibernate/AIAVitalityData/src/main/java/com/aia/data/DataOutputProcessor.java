package com.aia.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.dao.DbConnectionFactory;
import com.aia.eloqua.process.HKAGProcess;
import com.aia.eloqua.send.HKAGSend;
import com.aia.model.CDODetails;
import com.aia.model.CommonModel;
import com.aia.model.HKAchieveGold;
import com.aia.service.AIAService;

public class DataOutputProcessor {
	static Logger logger = Logger.getLogger(DataOutputProcessor.class);

	public static void sendToElqua(String key) {
		Class fileClass = FileToObjectList.getClassFromFile(key);

		if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveGold")) {

			HKAGSend.sendToElqua();
		}

	}

	public static void sendDistinctDuplicateToElqua(String key) {
		Class fileClass = FileToObjectList.getClassFromFile(key);

		if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveGold")) {
			HKAGSend.sendDistinctDuplicateToElqua();
		}

	}

}
