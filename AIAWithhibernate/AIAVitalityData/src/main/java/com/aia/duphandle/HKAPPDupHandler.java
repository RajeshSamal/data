package com.aia.duphandle;

import java.util.ArrayList;
import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.dao.HkappDAO;
import com.aia.data.DataInputProcessor;
import com.aia.data.HandleDuplicates;
import com.aia.model.HKApproachPlatinum;;

public class HKAPPDupHandler {
	
	
	
	public static List<HKApproachPlatinum> handleDuplicate(List<HKApproachPlatinum> objectList){
		
		List<HKApproachPlatinum> existingDuplicates = ((HkappDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_PLATINUM))).getListAsStatus(Constants.RECORD_DUPLICATE);
		List<HKApproachPlatinum> existingToBeSave = ((HkappDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_PLATINUM))).getListAsStatus(Constants.RECORD_SAVED);
		List<HKApproachPlatinum> statusWithSave = new ArrayList<HKApproachPlatinum>();
		List<HKApproachPlatinum> safeToSave = new ArrayList<HKApproachPlatinum>();
		
		for(HKApproachPlatinum object : objectList){
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
