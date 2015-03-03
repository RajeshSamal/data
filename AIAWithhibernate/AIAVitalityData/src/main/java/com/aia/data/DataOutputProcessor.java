package com.aia.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.aia.model.CDODetails;
import com.aia.model.CommonModel;
import com.aia.model.HKAchieveGold;
import com.aia.service.AIAService;

public class DataOutputProcessor {
	static Logger logger = Logger.getLogger(DataOutputProcessor.class);
	
	private static void sendToElqua(List objectList, Class fileClass) {
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

	}
	
	private static CDODetails processHKAG(CommonModel model) {

		CDODetails cdoData = new CDODetails();

		cdoData.setAiaVitalityMemberNumber(model.getAiaVitalityMemberNumber());
		cdoData.setClientId(model.getClientId());
		cdoData.setEmailAddress(model.getEmailAddress());
		cdoData.setGender(model.getGender());
		cdoData.setEntityReferenceNumber(model.getEntityReferenceNumber());
		cdoData.setLanguagePreference(model.getLanguagePreference());
		cdoData.setMemberFirstName(model.getMemberFirstNames());
		cdoData.setMemberSurname(model.getMemberSurname());
		cdoData.setPointBalance(model.getPointsBalance());
		cdoData.setVitalityStatus(model.getVitalityStatus());
		return cdoData;

	}

}
