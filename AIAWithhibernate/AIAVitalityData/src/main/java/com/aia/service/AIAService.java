/**
 * 
 */
package com.aia.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eloqua.api.bulk.helper.ImportCustomDataObjectHelper;
import com.eloqua.api.models.Import;
import com.eloqua.api.models.RestObjectList;
import com.eloqua.api.models.Sync;
import com.eloqua.api.models.SyncResult;
import com.aia.common.utils.Constants;
import com.aia.dao.CustomObjectDao;
import com.aia.data.DataInputProcessor;
import com.aia.model.CDODetails;
import com.aia.model.Eloqua;

/**
 * @author verticurl
 *
 */
public class AIAService {

	/** The Logger instance */
	private static final Logger LOG = LoggerFactory.getLogger(AIAService.class);
	private String custObjectId;


	/***
	 * create the CDO records in eloqua.
	 * 
	 * @param cdoDetailsList
	 */
	public void syncDataToEloqua(List<CDODetails> cdoDetailsList, String fileType) {
		Eloqua eloqua = DataInputProcessor.eloquaDao.getEloquaDetails();
		custObjectId = DataInputProcessor.customDao.getCustomObjectId(fileType);

		ImportCustomDataObjectHelper cdoHelper = new ImportCustomDataObjectHelper(
				eloqua.getSite(), eloqua.getUser(), eloqua.getPassword(), eloqua.getSite());

			Map<String, String> importFields = null;
			
			if (cdoDetailsList.size() > 0) {
				if(fileType.equals(Constants.HK_GOLD_ARCHIVE)){
					importFields = prepareHKAGImportField();
				}
				
				Import importStruc = cdoHelper.createImportStructure(
						custObjectId, importFields);
				if (importStruc != null) {
					String importUri = importStruc.getUri();
					List<Map<String, Object>> cdoDataList= null;
					// import the data
					if(fileType.equals(Constants.HK_GOLD_ARCHIVE)){
						cdoDataList = getCDOListForHKAG(cdoDetailsList);
					}

					if (cdoDataList != null) {
						Sync sync = cdoHelper
								.importData(importUri, cdoDataList);

						// poll results until the sync is complete
						RestObjectList<SyncResult> syncResult = cdoHelper
								.checkSyncResult(sync.getUri());

						if (syncResult != null
								&& syncResult.getElements() != null) {
							List<SyncResult> results = syncResult.getElements();
							for (SyncResult syncRes : results) {
								if (Constants.ELQ_ERROR
										.equalsIgnoreCase(syncRes.getSeverity())) {

									LOG.error(syncRes.getMessage());
								} else if (Constants.ELQ_WARNING
										.equalsIgnoreCase(syncRes.getSeverity())) {
									LOG.warn(syncRes.getMessage());
								}
							}
						}
					}
				}
			

		} 

	}
	
	private Map<String, String> prepareHKAGImportField(){
		Map<String, String> importFields = new HashMap<String, String>();
		CustomObjectDao dao= DataInputProcessor.customDao;
		String fileType= Constants.HK_GOLD_ARCHIVE;
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
		
		return importFields;
		
		
	}

	private List<Map<String, Object>> getCDOListForHKAG(
			List<CDODetails> cdoDetailsList) {

		List<Map<String, Object>> cdoDataList = new ArrayList<Map<String, Object>>();
		for (CDODetails cdoDetail : cdoDetailsList) {
			cdoDataList.add(prepareHKAGLeadData(cdoDetail));
		}
		return cdoDataList;
	}

	private Map<String, Object> prepareHKAGLeadData(CDODetails cdoDetail) {
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
