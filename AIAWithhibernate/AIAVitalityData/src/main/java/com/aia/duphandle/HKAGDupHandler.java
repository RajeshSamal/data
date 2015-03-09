package com.aia.duphandle;

import java.util.ArrayList;
import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.data.DataInputProcessor;
import com.aia.data.HandleDuplicates;
import com.aia.model.HKAchieveGold;

public class HKAGDupHandler {
	
	
	
	public static List<HKAchieveGold> handleDuplicateHKAG(List<HKAchieveGold> objectList){
		
		List<HKAchieveGold> existingDuplicates = DataInputProcessor.hkagDAO.getListAsStatus(Constants.RECORD_DUPLICATE);
		List<HKAchieveGold> existingToBeSave = DataInputProcessor.hkagDAO.getListAsStatus(Constants.RECORD_SAVED);
		List<HKAchieveGold> statusWithSave = new ArrayList<HKAchieveGold>();
		List<HKAchieveGold> safeToSave = new ArrayList<HKAchieveGold>();
		
		for(HKAchieveGold object : objectList){
			if(existingDuplicates != null && existingDuplicates.contains(object)){
				object.setRecordStatus(Constants.RECORD_DUPLICATE);
				safeToSave.add(object);
				
			}
			else if((existingToBeSave !=null) && existingToBeSave.contains(object)){
				object.setRecordStatus(Constants.RECORD_DUPLICATE);
				safeToSave.add(object);
			}
			else if((statusWithSave.size() >0) && statusWithSave.contains(object)){
				object.setRecordStatus(Constants.RECORD_DUPLICATE);
				safeToSave.add(object);
			}
			else{
				object.setRecordStatus(Constants.RECORD_SAVED);
				statusWithSave.add(object);
				safeToSave.add(object);
			}
			
		}
		
		
		HandleDuplicates.noOdDuplicate= (objectList.size()-statusWithSave.size());
		return safeToSave;
		
	}

}
