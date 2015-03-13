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
import com.aia.dao.EloquaDao;
import com.aia.data.DataInputProcessor;
import com.aia.eloqua.process.HKAGProcess;
import com.aia.eloqua.process.HKAMCRProcess;
import com.aia.eloqua.process.HKAPGProcess;
import com.aia.eloqua.process.HKAPPProcess;
import com.aia.eloqua.process.HKAPProcess;
import com.aia.eloqua.process.HKAPSProcess;
import com.aia.eloqua.process.HKASProcess;
import com.aia.eloqua.process.HKDWProcess;
import com.aia.eloqua.process.HKER1Process;
import com.aia.eloqua.process.HKER2Process;
import com.aia.eloqua.process.HKER3Process;
import com.aia.eloqua.process.HKMYAProcess;
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
		Eloqua eloqua = ((EloquaDao)(DataInputProcessor.getDao(Constants.ELOQUA))).getEloquaDetails();
		custObjectId = ((CustomObjectDao)(DataInputProcessor.getDao(Constants.CUSTOM))).getCustomObjectId(fileType);

		ImportCustomDataObjectHelper cdoHelper = new ImportCustomDataObjectHelper(
				eloqua.getSite(), eloqua.getUser(), eloqua.getPassword(), eloqua.getBaseUrl());

			Map<String, String> importFields = null;
			
			if (cdoDetailsList.size() > 0) {
				if(fileType.equals(Constants.HK_GOLD_ARCHIVE)){
					importFields = HKAGProcess.importFields;
				}
				else if(fileType.equals(Constants.HK_PLATINUM_ARCHIVE)){
					importFields = HKAPProcess.importFields;
					
				}
				else if(fileType.equals(Constants.HK_SILVER_ARCHIVE)){
					importFields = HKASProcess.importFields;
					
				}
				else if(fileType.equals(Constants.HK_ENGAGEMENT_REMINDER1)){
					importFields = HKER1Process.importFields;
					
				}
				else if(fileType.equals(Constants.HK_ENGAGEMENT_REMINDER2)){
					importFields = HKER2Process.importFields;
					
				}
				else if(fileType.equals(Constants.HK_ENGAGEMENT_REMINDER3)){
					importFields = HKER3Process.importFields;
					
				}
				else if(fileType.equals(Constants.HK_ADIDAS_MICOACH_CONCENT_REMINDER)){
					importFields = HKAMCRProcess.importFields;
					
				}
				else if(fileType.equals(Constants.HK_DOWNGRADE_WARNING)){
					importFields = HKDWProcess.importFields;
					
				}
				else if(fileType.equals(Constants.HK_MID_YEAR_ASSESSMENT)){
					importFields = HKMYAProcess.importFields;
					
				}
				else if(fileType.equals(Constants.HK_APPROACH_GOLD)){
					importFields = HKAPGProcess.importFields;
					
				}
				else if(fileType.equals(Constants.HK_APPROACH_PLATINUM)){
					importFields = HKAPPProcess.importFields;
					
				}
				else if(fileType.equals(Constants.HK_APPROACH_SILVER)){
					importFields = HKAPSProcess.importFields;
					
				}
				
				Import importStruc = cdoHelper.createImportStructure(
						custObjectId, importFields);
				if (importStruc != null) {
					String importUri = importStruc.getUri();
					List<Map<String, Object>> cdoDataList= null;
					// import the data
					if(fileType.equals(Constants.HK_GOLD_ARCHIVE)){
						cdoDataList = HKAGProcess.getCDOList(cdoDetailsList);
					}
					else if(fileType.equals(Constants.HK_PLATINUM_ARCHIVE)){
						cdoDataList = HKAPProcess.getCDOList(cdoDetailsList);
						
					}
					else if(fileType.equals(Constants.HK_SILVER_ARCHIVE)){
						cdoDataList = HKASProcess.getCDOList(cdoDetailsList);
						
					}
					else if(fileType.equals(Constants.HK_ENGAGEMENT_REMINDER1)){
						cdoDataList = HKER1Process.getCDOList(cdoDetailsList);
						
					}
					else if(fileType.equals(Constants.HK_ENGAGEMENT_REMINDER2)){
						cdoDataList = HKER2Process.getCDOList(cdoDetailsList);
						
					}
					else if(fileType.equals(Constants.HK_ENGAGEMENT_REMINDER3)){
						cdoDataList = HKER3Process.getCDOList(cdoDetailsList);
						
					}
					
					else if(fileType.equals(Constants.HK_ADIDAS_MICOACH_CONCENT_REMINDER)){
						cdoDataList = HKAMCRProcess.getCDOList(cdoDetailsList);
						
					}
					else if(fileType.equals(Constants.HK_DOWNGRADE_WARNING)){
						cdoDataList = HKDWProcess.getCDOList(cdoDetailsList);
						
					}
					else if(fileType.equals(Constants.HK_MID_YEAR_ASSESSMENT)){
						cdoDataList = HKMYAProcess.getCDOList(cdoDetailsList);
					}
					else if(fileType.equals(Constants.HK_APPROACH_GOLD)){
						cdoDataList = HKAPGProcess.getCDOList(cdoDetailsList);
					}
					else if(fileType.equals(Constants.HK_APPROACH_PLATINUM)){
						cdoDataList = HKAPPProcess.getCDOList(cdoDetailsList);
					}
					else if(fileType.equals(Constants.HK_APPROACH_SILVER)){
						cdoDataList = HKAPSProcess.getCDOList(cdoDetailsList);
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
