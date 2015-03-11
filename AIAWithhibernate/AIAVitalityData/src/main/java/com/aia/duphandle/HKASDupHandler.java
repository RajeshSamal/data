package com.aia.duphandle;

import java.util.ArrayList;
import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.dao.HkasDAO;
import com.aia.data.DataInputProcessor;
import com.aia.data.HandleDuplicates;
import com.aia.model.HKAchieveSilver;

public class HKASDupHandler {
	
	
	
	public static List<HKAchieveSilver> handleDuplicateHKAS(List<HKAchieveSilver> objectList){
		
		List<HKAchieveSilver> existingDuplicates = ((HkasDAO)(DataInputProcessor.getDao(Constants.HK_SILVER_ARCHIVE))).getListAsStatus(Constants.RECORD_DUPLICATE);
		List<HKAchieveSilver> existingToBeSave = ((HkasDAO)(DataInputProcessor.getDao(Constants.HK_SILVER_ARCHIVE))).getListAsStatus(Constants.RECORD_SAVED);
		List<HKAchieveSilver> statusWithSave = new ArrayList<HKAchieveSilver>();
		List<HKAchieveSilver> safeToSave = new ArrayList<HKAchieveSilver>();
		
		for(HKAchieveSilver object : objectList){
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
