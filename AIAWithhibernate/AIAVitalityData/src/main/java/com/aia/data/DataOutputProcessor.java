package com.aia.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.aia.model.CDODetails;
import com.aia.model.HKAchieveGold;
import com.aia.service.AIAService;

public class DataOutputProcessor {
	static Logger logger = Logger.getLogger(DataOutputProcessor.class);
	
	/*private static void sendToElqua(List objectList, Class fileClass) {
		// String className = fileClass.getName();
		// Class.forName(className).cast(obj);
		List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
		HKAchieveGold HKAG = null;
		if (fileClass.getName().equalsIgnoreCase("com.aia.model.HKAchieveGold")) {
			for (int i = 0; i < objectList.size(); i++) {

				HKAG = (HKAchieveGold) objectList.get(i);
				CDODetails cdoData = processHKAG(HKAG);
				cdoDetailsList.add(cdoData);
			}

		}

		AIAService aiaService = new AIAService();
		aiaService.syncCDODataToEloqua(cdoDetailsList);

	}*/

}
