package com.aia.data;

import java.util.List;

import com.aia.duphandle.HKAGDupHandler;
import com.aia.duphandle.HKAPDupHandler;
import com.aia.duphandle.HKASDupHandler;
import com.aia.duphandle.HKER1DupHandler;
import com.aia.duphandle.HKER2DupHandler;
import com.aia.duphandle.HKER3DupHandler;

public class HandleDuplicates {
	public static int noOdDuplicate = 0;
	
	public static List handleDuplicate(List objectList, Class fileClass){
		
		if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveGold")) {
			objectList = HKAGDupHandler.handleDuplicateHKAG(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchievePlatinum")) {
			objectList = HKAPDupHandler.handleDuplicateHKAP(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveSilver")) {
			objectList = HKASDupHandler.handleDuplicateHKAS(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder1")) {
			objectList = HKER1DupHandler.handleDuplicateHKER1(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder2")) {
			objectList = HKER2DupHandler.handleDuplicateHKER2(objectList);
		}
		else if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKEngagementReminder3")) {
			objectList = HKER3DupHandler.handleDuplicateHKER3(objectList);
		}
		return objectList;
	}

}
