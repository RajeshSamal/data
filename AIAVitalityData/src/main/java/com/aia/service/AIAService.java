/**
 * 
 */
package com.aia.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eloqua.api.bulk.helper.ImportCustomDataObjectHelper;
import com.eloqua.api.models.Import;
import com.eloqua.api.models.RestObjectList;
import com.eloqua.api.models.Sync;
import com.eloqua.api.models.SyncResult;
import com.aia.common.utils.Constants;
import com.aia.model.CDODetails;

/**
 * @author verticurl
 *
 */
public class AIAService {

	/** The Logger instance */
	private static final Logger LOG = LoggerFactory.getLogger(AIAService.class);
	private String custObjectId = "15";

	private static String site = "";
	private static String user = "";
	private static String password = "";
	private static String baseUrl = "";

	static {
		Properties prop = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream("config\\eloqua.properties");

			// load a properties file
			prop.load(input);

			Enumeration e = prop.propertyNames();
			site = prop.getProperty("site");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			baseUrl = prop.getProperty("baseUrl");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/***
	 * create the CDO records in eloqua.
	 * 
	 * @param cdoDetailsList
	 */
	public void syncCDODataToEloqua(List<CDODetails> cdoDetailsList) {

		ImportCustomDataObjectHelper cdoHelper = new ImportCustomDataObjectHelper(
				site, user, password, baseUrl);

		try {
			Map<String, String> importFields = new HashMap<String, String>();

			if (cdoDetailsList.size() > 0) {

				// importFields.put("Date_Created1",
				// "{{CustomObject[18].Field[178]}}");
				// importFields.put("Date_Modified1",
				// "{{CustomObject[18].Field[179]}}");
				importFields.put("LANGUAGE_PREFERENCE1", "{{CustomObject["
						+ custObjectId + "].Field[367]}}");
				importFields.put("CLIENT_ID1", "{{CustomObject[" + custObjectId
						+ "].Field[368]}}");
				importFields.put("GENDER1", "{{CustomObject[" + custObjectId
						+ "].Field[369]}}");
				importFields.put("POINTS_BALANCE1", "{{CustomObject["
						+ custObjectId + "].Field[370]}}");
				importFields.put("VITALITY_STATUS1", "{{CustomObject["
						+ custObjectId + "].Field[371]}}");
				importFields.put("ENTITY_REFERENCE_NUMBER1", "{{CustomObject["
						+ custObjectId + "].Field[372]}}");
				importFields.put("AIA_VITALITY_MEMBER_NUMBER1",
						"{{CustomObject[" + custObjectId + "].Field[373]}}");
				importFields.put("EMAIL_ADDRESS1", "{{CustomObject["
						+ custObjectId + "].Field[374]}}");
				importFields.put("MEMBER_SURNAME1", "{{CustomObject["
						+ custObjectId + "].Field[375]}}");
				importFields.put("MEMBER_FIRST_NAMES1", "{{CustomObject["
						+ custObjectId + "].Field[376]}}");

				// importFields.put("Comments1",
				// "{{CustomObject["+custObjectId+"].Field["+custom_lead_field_comments+"]}}");
				// importFields.put("EmailAddressField",
				// "{{CustomObject["+custObjectId+"].Contact.Field(C_EmailAddress)}}");

				// create the structure for the import
				Import importStruc = cdoHelper.createImportStructure(
						custObjectId, importFields);

				if (importStruc != null) {
					String importUri = importStruc.getUri();

					// import the data
					List<Map<String, Object>> cdoDataList = getCDOListForElq(cdoDetailsList);

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

		} catch (Exception e) {
			LOG.error("Exception occured while creating lead to Eloqua. {0}", e);

		}

	}

	private List<Map<String, Object>> getCDOListForElq(
			List<CDODetails> cdoDetailsList) {

		List<Map<String, Object>> cdoDataList = new ArrayList<Map<String, Object>>();
		for (CDODetails cdoDetail : cdoDetailsList) {
			cdoDataList.add(prepareLeadData(cdoDetail));
		}
		return cdoDataList;
	}

	private Map<String, Object> prepareLeadData(CDODetails cdoDetail) {
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
