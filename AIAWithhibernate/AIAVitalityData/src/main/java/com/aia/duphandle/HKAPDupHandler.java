package com.aia.duphandle;

import java.util.ArrayList;
import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.data.DataInputProcessor;
import com.aia.data.HandleDuplicates;
import com.aia.model.HKAchievePlatinum;

public class HKAPDupHandler {
	
	
	
	public static List<HKAchievePlatinum> handleDuplicateHKAP(List<HKAchievePlatinum> objectList){
		
		List<HKAchievePlatinum> existingDuplicates = DataInputProcessor.hkapDAO.getListAsStatus(Constants.RECORD_DUPLICATE);
		List<HKAchievePlatinum> existingToBeSave = DataInputProcessor.hkapDAO.getListAsStatus(Constants.RECORD_SAVED);
		List<HKAchievePlatinum> statusWithSave = new ArrayList<HKAchievePlatinum>();
		List<HKAchievePlatinum> safeToSave = new ArrayList<HKAchievePlatinum>();
		
		for(HKAchievePlatinum object : objectList){
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
