package com.aia.data;

import java.util.List;

import com.aia.duphandle.HKAGDupHandler;

public class HandleDuplicates {
	public static int noOdDuplicate = 0;
	
	public static List handleDuplicate(List objectList, Class fileClass){
		
		if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveGold")) {
			objectList = HKAGDupHandler.handleDuplicateHKAG(objectList);
		}
		return objectList;
	}

}
