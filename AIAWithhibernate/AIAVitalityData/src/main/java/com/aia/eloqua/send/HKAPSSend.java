package com.aia.eloqua.send;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.dao.DataFileDao;
import com.aia.dao.DbConnectionFactory;
import com.aia.dao.HkapsDAO;
import com.aia.data.DataInputProcessor;
import com.aia.eloqua.process.HKAPPProcess;
import com.aia.eloqua.process.HKAPSProcess;
import com.aia.model.CDODetails;
import com.aia.model.DataFile;
import com.aia.model.HKApproachSilver;
import com.aia.service.AIAService;

public class HKAPSSend {

	public static void sendDistinctDuplicateToElqua() {

		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKApproachSilver HKAPS = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_APPROACH_SILVER;
			List<HKApproachSilver> objectList = ((HkapsDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_SILVER)))
					.getDistnctDuplicates();
			Set<HKApproachSilver> duplicateSet = new HashSet<HKApproachSilver>(
					objectList);
			List<HKApproachSilver> list = new ArrayList<HKApproachSilver>(duplicateSet);

			for (int i = 0; i < list.size(); i++) {
			
				HKAPS = list.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAPSProcess.process(HKAPS);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKAPS = (HKApproachSilver) list.get(i);
					HKAPS.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = ((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).get(HKAPS.getFileName());
					if(fileList.size()>0){
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords()-1);
						((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).update(file);
					}
				}
			}
			
			((HkapsDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_SILVER))).updateList(list, session);

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	public static void sendToElqua() {
		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKApproachSilver HKAPS = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_APPROACH_SILVER;
			List<HKApproachSilver> objectList = ((HkapsDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_SILVER)))
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKAPS = (HKApproachSilver) objectList.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAPSProcess.process(HKAPS);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKAPS = (HKApproachSilver) objectList.get(i);
					HKAPS.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			((HkapsDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_SILVER))).updateList(objectList, session);

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

}
