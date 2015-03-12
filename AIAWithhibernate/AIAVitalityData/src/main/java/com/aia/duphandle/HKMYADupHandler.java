package com.aia.duphandle;

import java.util.ArrayList;
import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.dao.HkmyaDAO;
import com.aia.data.DataInputProcessor;
import com.aia.data.HandleDuplicates;
import com.aia.model.HKDowngradeWarning;;

public class HKMYADupHandler {
	
	
	
	public static List<HKDowngradeWarning> handleDuplicate(List<HKDowngradeWarning> objectList){
		
		List<HKDowngradeWarning> existingDuplicates = ((HkmyaDAO)(DataInputProcessor.getDao(Constants.HK_MID_YEAR_ASSESSMENT))).getListAsStatus(Constants.RECORD_DUPLICATE);
		List<HKDowngradeWarning> existingToBeSave = ((HkmyaDAO)(DataInputProcessor.getDao(Constants.HK_MID_YEAR_ASSESSMENT))).getListAsStatus(Constants.RECORD_SAVED);
		List<HKDowngradeWarning> statusWithSave = new ArrayList<HKDowngradeWarning>();
		List<HKDowngradeWarning> safeToSave = new ArrayList<HKDowngradeWarning>();
		
		for(HKDowngradeWarning object : objectList){
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
