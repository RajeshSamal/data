package com.aia.duphandle;

import java.util.ArrayList;
import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.dao.Hker3DAO;
import com.aia.data.DataInputProcessor;
import com.aia.data.HandleDuplicates;
import com.aia.model.HKEngagementReminder3;

public class HKER3DupHandler {
	
	
	
	public static List<HKEngagementReminder3> handleDuplicateHKER3(List<HKEngagementReminder3> objectList){
		
		List<HKEngagementReminder3> existingDuplicates = ((Hker3DAO)(DataInputProcessor.getDao(Constants.HK_ENGAGEMENT_REMINDER3))).getListAsStatus(Constants.RECORD_DUPLICATE);
		List<HKEngagementReminder3> existingToBeSave = ((Hker3DAO)(DataInputProcessor.getDao(Constants.HK_ENGAGEMENT_REMINDER3))).getListAsStatus(Constants.RECORD_SAVED);
		List<HKEngagementReminder3> statusWithSave = new ArrayList<HKEngagementReminder3>();
		List<HKEngagementReminder3> safeToSave = new ArrayList<HKEngagementReminder3>();
		
		for(HKEngagementReminder3 object : objectList){
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
