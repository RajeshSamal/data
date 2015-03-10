package com.aia.duphandle;

import java.util.ArrayList;
import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.data.DataInputProcessor;
import com.aia.data.HandleDuplicates;
import com.aia.model.HKEngagementReminder1;

public class HKER1DupHandler {
	
	
	
	public static List<HKEngagementReminder1> handleDuplicateHKER1(List<HKEngagementReminder1> objectList){
		
		List<HKEngagementReminder1> existingDuplicates = DataInputProcessor.hker1DAO.getListAsStatus(Constants.RECORD_DUPLICATE);
		List<HKEngagementReminder1> existingToBeSave = DataInputProcessor.hker1DAO.getListAsStatus(Constants.RECORD_SAVED);
		List<HKEngagementReminder1> statusWithSave = new ArrayList<HKEngagementReminder1>();
		List<HKEngagementReminder1> safeToSave = new ArrayList<HKEngagementReminder1>();
		
		for(HKEngagementReminder1 object : objectList){
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
