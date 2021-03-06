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
import com.aia.duphandle.HKER1DupHandler;
import com.aia.eloqua.process.HKAGProcess;
import com.aia.eloqua.send.HKAGSend;
import com.aia.eloqua.send.HKAMCRSend;
import com.aia.eloqua.send.HKAPGSend;
import com.aia.eloqua.send.HKAPPSend;
import com.aia.eloqua.send.HKAPSSend;
import com.aia.eloqua.send.HKAPSend;
import com.aia.eloqua.send.HKASSend;
import com.aia.eloqua.send.HKDWSend;
import com.aia.eloqua.send.HKER1Send;
import com.aia.eloqua.send.HKER2Send;
import com.aia.eloqua.send.HKER3Send;
import com.aia.eloqua.send.HKMYASend;
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
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchievePlatinum")) {
			HKAPSend.sendToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveSilver")) {
			HKASSend.sendToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder1")) {
			HKER1Send.sendToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder2")) {
			HKER2Send.sendToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder3")) {
			HKER3Send.sendToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAdidasMicConRem")) {
			HKAMCRSend.sendToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKMidYearAssessment")) {
			HKMYASend.sendToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKDowngradeWarning")) {
			HKDWSend.sendToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKApproachGold")) {
			HKAPGSend.sendToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKApproachSilver")) {
			HKAPSSend.sendToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKApproachPlatinum")) {
			HKAPPSend.sendToElqua();
		}

	}

	public static void sendDistinctDuplicateToElqua(String key) {
		Class fileClass = FileToObjectList.getClassFromFile(key);

		if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveGold")) {
			HKAGSend.sendDistinctDuplicateToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchievePlatinum")) {
			HKAPSend.sendDistinctDuplicateToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveSilver")) {
			HKASSend.sendDistinctDuplicateToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder1")) {
			HKER1Send.sendDistinctDuplicateToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder2")) {
			HKER2Send.sendDistinctDuplicateToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder3")) {
			HKER3Send.sendDistinctDuplicateToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAdidasMicConRem")) {
			HKAMCRSend.sendDistinctDuplicateToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKMidYearAssessment")) {
			HKMYASend.sendDistinctDuplicateToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKDowngradeWarning")) {
			HKDWSend.sendDistinctDuplicateToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKApproachGold")) {
			HKAPGSend.sendDistinctDuplicateToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKApproachSilver")) {
			HKAPSSend.sendDistinctDuplicateToElqua();
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKApproachPlatinum")) {
			HKAPPSend.sendDistinctDuplicateToElqua();
		}

	}

}
