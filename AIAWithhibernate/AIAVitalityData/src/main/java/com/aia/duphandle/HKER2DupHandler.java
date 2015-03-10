package com.aia.duphandle;

import java.util.ArrayList;
import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.data.DataInputProcessor;
import com.aia.data.HandleDuplicates;
import com.aia.model.HKEngagementReminder2;

public class HKER2DupHandler {
	
	
	
	public static List<HKEngagementReminder2> handleDuplicateHKER2(List<HKEngagementReminder2> objectList){
		
		List<HKEngagementReminder2> existingDuplicates = DataInputProcessor.hker2DAO.getListAsStatus(Constants.RECORD_DUPLICATE);
		List<HKEngagementReminder2> existingToBeSave = DataInputProcessor.hker2DAO.getListAsStatus(Constants.RECORD_SAVED);
		List<HKEngagementReminder2> statusWithSave = new ArrayList<HKEngagementReminder2>();
		List<HKEngagementReminder2> safeToSave = new ArrayList<HKEngagementReminder2>();
		
		for(HKEngagementReminder2 object : objectList){
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
