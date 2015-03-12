package com.aia.duphandle;

import java.util.ArrayList;
import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.dao.HkamcrDAO;
import com.aia.data.DataInputProcessor;
import com.aia.data.HandleDuplicates;
import com.aia.model.HKAdidasMicConRem;;

public class HKAMCRDupHandler {
	
	
	
	public static List<HKAdidasMicConRem> handleDuplicate(List<HKAdidasMicConRem> objectList){
		
		List<HKAdidasMicConRem> existingDuplicates = ((HkamcrDAO)(DataInputProcessor.getDao(Constants.HK_ADIDAS_MICOACH_CONCENT_REMINDER))).getListAsStatus(Constants.RECORD_DUPLICATE);
		List<HKAdidasMicConRem> existingToBeSave = ((HkamcrDAO)(DataInputProcessor.getDao(Constants.HK_ADIDAS_MICOACH_CONCENT_REMINDER))).getListAsStatus(Constants.RECORD_SAVED);
		List<HKAdidasMicConRem> statusWithSave = new ArrayList<HKAdidasMicConRem>();
		List<HKAdidasMicConRem> safeToSave = new ArrayList<HKAdidasMicConRem>();
		
		for(HKAdidasMicConRem object : objectList){
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
