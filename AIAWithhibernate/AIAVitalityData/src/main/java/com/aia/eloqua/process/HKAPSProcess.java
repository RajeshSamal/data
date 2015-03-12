package com.aia.eloqua.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aia.common.utils.Constants;
import com.aia.dao.CustomObjectDao;
import com.aia.data.DataInputProcessor;
import com.aia.model.CDODetails;
import com.aia.model.CommonModel;

public class HKAPSProcess {
	public static Map<String, String> importFields;
	
	
	
	static {
		importFields = new HashMap<String, String>();
		CustomObjectDao dao= ((CustomObjectDao)(DataInputProcessor.getDao(Constants.CUSTOM)));
		String fileType= Constants.HK_GOLD_ARCHIVE;
		String custObjectId = dao.getCustomObjectId(fileType);
		String languagePrefernceID = dao.getCustomFieldId(fileType, "LANGUAGE_PREFERENCE1");
		String clientID = dao.getCustomFieldId(fileType, "CLIENT_ID1");
		String genderId = dao.getCustomFieldId(fileType, "GENDER1");
		String pointBalanceID = dao.getCustomFieldId(fileType, "POINTS_BALANCE1");
		String vitalityStatusID = dao.getCustomFieldId(fileType, "VITALITY_STATUS1");
		String EntityReferenceID = dao.getCustomFieldId(fileType, "ENTITY_REFERENCE_NUMBER1");
		String aiaVitalityNumberID = dao.getCustomFieldId(fileType, "AIA_VITALITY_MEMBER_NUMBER1");
		String emailAddressID = dao.getCustomFieldId(fileType, "EMAIL_ADDRESS1");
		String memberSurNmaeID = dao.getCustomFieldId(fileType, "MEMBER_SURNAME1");
		String memberFirstNameID = dao.getCustomFieldId(fileType, "MEMBER_FIRST_NAMES1");
		
		importFields.put("LANGUAGE_PREFERENCE1", "{{CustomObject["
				+ custObjectId + "].Field[" + languagePrefernceID+ "]}}");
		
		importFields.put("CLIENT_ID1", "{{CustomObject[" + custObjectId
				+ "].Field["+clientID+"]}}");
		
		importFields.put("GENDER1", "{{CustomObject[" + custObjectId
				+ "].Field["+genderId+"]}}");
		
		importFields.put("POINTS_BALANCE1", "{{CustomObject["
				+ custObjectId + "].Field["+pointBalanceID+"]}}");
		
		importFields.put("VITALITY_STATUS1", "{{CustomObject["
				+ custObjectId + "].Field["+vitalityStatusID+"]}}");
		
		importFields.put("ENTITY_REFERENCE_NUMBER1", "{{CustomObject["
				+ custObjectId + "].Field["+EntityReferenceID+"]}}");
		
		importFields.put("AIA_VITALITY_MEMBER_NUMBER1",
				"{{CustomObject[" + custObjectId + "].Field["+aiaVitalityNumberID+"]}}");
		
		importFields.put("EMAIL_ADDRESS1", "{{CustomObject["
				+ custObjectId + "].Field["+emailAddressID+"]}}");
		
		importFields.put("MEMBER_SURNAME1", "{{CustomObject["
				+ custObjectId + "].Field["+memberSurNmaeID+"]}}");
		
		importFields.put("MEMBER_FIRST_NAMES1", "{{CustomObject["
				+ custObjectId + "].Field["+memberFirstNameID+"]}}");
		
		
		
		
	}
	
	public static CDODetails processHKAG(CommonModel model) {

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
	
	
	public static List<Map<String, Object>> getCDOListForHKAG(
			List<CDODetails> cdoDetailsList) {

		List<Map<String, Object>> cdoDataList = new ArrayList<Map<String, Object>>();
		for (CDODetails cdoDetail : cdoDetailsList) {
			cdoDataList.add(prepareHKAGLeadData(cdoDetail));
		}
		return cdoDataList;
	}

	private static Map<String, Object> prepareHKAGLeadData(CDODetails cdoDetail) {
		Map<String, Object> cdoData = new HashMap<String, Object>();

		if (cdoDetail != null) {

			cdoData.put("LANGUAGE_PREFERENCE1",
					cdoDetail.getLanguagePreference());
			cdoData.put("CLIENT_ID1", cdoDetail.getClientId());
			cdoData.put("GENDER1", cdoDetail.getGender());
			cdoData.put("POINTS_BALANCE1", cdoDetail.getPointBalance());
			cdoData.put("VITALITY_STATUS1", cdoDetail.getVitalityStatus());
			cdoData.put("ENTITY_REFERENCE_NUMBER1",
					cdoDetail.getEntityReferenceNumber());
			cdoData.put("AIA_VITALITY_MEMBER_NUMBER1",
					cdoDetail.getAiaVitalityMemberNumber());
			cdoData.put("EMAIL_ADDRESS1", cdoDetail.getEmailAddress());
			cdoData.put("MEMBER_SURNAME1", cdoDetail.getMemberSurname());
			cdoData.put("MEMBER_FIRST_NAMES1", cdoDetail.getMemberFirstName());

		}
		return cdoData;
	}
	
	


}
