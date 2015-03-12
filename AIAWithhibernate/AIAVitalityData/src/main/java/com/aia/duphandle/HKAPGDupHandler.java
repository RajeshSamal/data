package com.aia.duphandle;

import java.util.ArrayList;
import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.dao.HkapgDAO;
import com.aia.data.DataInputProcessor;
import com.aia.data.HandleDuplicates;
import com.aia.model.HKApproachGold;;

public class HKAPGDupHandler {
	
	
	
	public static List<HKApproachGold> handleDuplicate(List<HKApproachGold> objectList){
		
		List<HKApproachGold> existingDuplicates = ((HkapgDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_GOLD))).getListAsStatus(Constants.RECORD_DUPLICATE);
		List<HKApproachGold> existingToBeSave = ((HkapgDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_GOLD))).getListAsStatus(Constants.RECORD_SAVED);
		List<HKApproachGold> statusWithSave = new ArrayList<HKApproachGold>();
		List<HKApproachGold> safeToSave = new ArrayList<HKApproachGold>();
		
		for(HKApproachGold object : objectList){
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
