package com.aia.duphandle;

import java.util.ArrayList;
import java.util.List;

import com.aia.common.utils.Constants;
import com.aia.dao.HkmyaDAO;
import com.aia.data.DataInputProcessor;
import com.aia.data.HandleDuplicates;
import com.aia.model.HKMidYearAssessment;

public class HKMYADupHandler {
	
	
	
	public static List<HKMidYearAssessment> handleDuplicate(List<HKMidYearAssessment> objectList){
		
		List<HKMidYearAssessment> existingDuplicates = ((HkmyaDAO)(DataInputProcessor.getDao(Constants.HK_MID_YEAR_ASSESSMENT))).getListAsStatus(Constants.RECORD_DUPLICATE);
		List<HKMidYearAssessment> existingToBeSave = ((HkmyaDAO)(DataInputProcessor.getDao(Constants.HK_MID_YEAR_ASSESSMENT))).getListAsStatus(Constants.RECORD_SAVED);
		List<HKMidYearAssessment> statusWithSave = new ArrayList<HKMidYearAssessment>();
		List<HKMidYearAssessment> safeToSave = new ArrayList<HKMidYearAssessment>();
		
		for(HKMidYearAssessment object : objectList){
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
