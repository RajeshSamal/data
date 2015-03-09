package com.aia.data;

import java.util.List;

import com.aia.duphandle.HKAGDupHandler;
import com.aia.duphandle.HKAPDupHandler;
import com.aia.duphandle.HKASDupHandler;

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
		return objectList;
	}

}
