package com.aia.data;

import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.dao.HkamcrDAO;
import com.aia.dao.HkapgDAO;
import com.aia.dao.HkappDAO;
import com.aia.dao.HkapsDAO;
import com.aia.dao.HkdwDAO;
import com.aia.dao.HkmyaDAO;
import com.aia.duphandle.HKAGDupHandler;
import com.aia.duphandle.HKAMCRDupHandler;
import com.aia.duphandle.HKAPDupHandler;
import com.aia.duphandle.HKAPGDupHandler;
import com.aia.duphandle.HKAPPDupHandler;
import com.aia.duphandle.HKAPSDupHandler;
import com.aia.duphandle.HKASDupHandler;
import com.aia.duphandle.HKDWDupHandler;
import com.aia.duphandle.HKER1DupHandler;
import com.aia.duphandle.HKER2DupHandler;
import com.aia.duphandle.HKER3DupHandler;
import com.aia.duphandle.HKMYADupHandler;

public class HandleDuplicates {
	public static int noOdDuplicate = 0;
	
	public static List handleDuplicate(List objectList, Class fileClass){
		
		if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveGold")) {
			objectList = HKAGDupHandler.handleDuplicate(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchievePlatinum")) {
			objectList = HKAPDupHandler.handleDuplicate(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveSilver")) {
			objectList = HKASDupHandler.handleDuplicate(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder1")) {
			objectList = HKER1DupHandler.handleDuplicate(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder2")) {
			objectList = HKER2DupHandler.handleDuplicate(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder3")) {
			objectList = HKER3DupHandler.handleDuplicate(objectList);
		}
		
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAdidasMicConRem")) {
			objectList = HKAMCRDupHandler.handleDuplicate(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKMidYearAssessment")) {
			objectList = HKMYADupHandler.handleDuplicate(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKDowngradeWarning")) {
			objectList = HKDWDupHandler.handleDuplicate(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKApproachGold")) {
			objectList = HKAPGDupHandler.handleDuplicate(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKApproachSilver")) {
			objectList = HKAPSDupHandler.handleDuplicate(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKApproachPlatinum")) {
			objectList = HKAPPDupHandler.handleDuplicate(objectList);
		}
		return objectList;
	}

}
