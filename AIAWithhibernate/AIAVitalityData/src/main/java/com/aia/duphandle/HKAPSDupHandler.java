package com.aia.duphandle;

import java.util.ArrayList;
import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.dao.HkapsDAO;
import com.aia.data.DataInputProcessor;
import com.aia.data.HandleDuplicates;
import com.aia.model.HKApproachSilver;

public class HKAPSDupHandler {
	
	
	
	public static List<HKApproachSilver> handleDuplicate(List<HKApproachSilver> objectList){
		
		List<HKApproachSilver> existingDuplicates = ((HkapsDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_SILVER))).getListAsStatus(Constants.RECORD_DUPLICATE);
		List<HKApproachSilver> existingToBeSave = ((HkapsDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_SILVER))).getListAsStatus(Constants.RECORD_SAVED);
		List<HKApproachSilver> statusWithSave = new ArrayList<HKApproachSilver>();
		List<HKApproachSilver> safeToSave = new ArrayList<HKApproachSilver>();
		
		for(HKApproachSilver object : objectList){
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
