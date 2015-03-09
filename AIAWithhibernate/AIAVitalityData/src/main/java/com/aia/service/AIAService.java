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
import com.aia.eloqua.process.HKAGProcess;
import com.aia.model.CDODetails;
import com.aia.model.Eloqua;

/**
 * @author verticurl
 *
 */
public class AIAService {

	/** The Logger instance */
	private static final Logger LOG = LoggerFactory.getLogger(AIAService.class);
	private static String custObjectId;


	/***
	 * create the CDO records in eloqua.
	 * 
	 * @param cdoDetailsList
	 */
	public static int syncDataToEloqua(List<CDODetails> cdoDetailsList, String fileType) {
		int status= -1;
		Eloqua eloqua = DataInputProcessor.eloquaDao.getEloquaDetails();
		custObjectId = DataInputProcessor.customDao.getCustomObjectId(fileType);

		ImportCustomDataObjectHelper cdoHelper = new ImportCustomDataObjectHelper(
				eloqua.getSite(), eloqua.getUser(), eloqua.getPassword(), eloqua.getBaseUrl());

			Map<String, String> importFields = null;
			
			if (cdoDetailsList.size() > 0) {
				if(fileType.equals(Constants.HK_GOLD_ARCHIVE)){
					importFields = HKAGProcess.importFields;
				}
				
				Import importStruc = cdoHelper.createImportStructure(
						custObjectId, importFields);
				if (importStruc != null) {
					String importUri = importStruc.getUri();
					List<Map<String, Object>> cdoDataList= null;
					// import the data
					if(fileType.equals(Constants.HK_GOLD_ARCHIVE)){
						cdoDataList = HKAGProcess.getCDOListForHKAG(cdoDetailsList);
					}

					if (cdoDataList != null) {
						Sync sync = cdoHelper
								.importData(importUri, cdoDataList);

						// poll results until the sync is complete
						RestObjectList<SyncResult> syncResult = cdoHelper
								.checkSyncResult(sync.getUri());

						if (syncResult != null
								&& syncResult.getElements() != null) {
							status=0;
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
			return status;
	}
	
	
	

}
