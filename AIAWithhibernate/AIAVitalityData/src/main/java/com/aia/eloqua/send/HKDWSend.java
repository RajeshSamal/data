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
import com.aia.dao.HkdwDAO;
import com.aia.data.DataInputProcessor;
import com.aia.model.CDODetails;
import com.aia.model.DataFile;
import com.aia.model.HKDowngradeWarning;
import com.aia.service.AIAService;

public class HKDWSend {

	public static void sendDistinctDuplicateToElqua() {

		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKDowngradeWarning HKDW = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_DOWNGRADE_WARNING;
			List<HKDowngradeWarning> objectList = (((HkdwDAO)(DataInputProcessor.getDao(Constants.HK_DOWNGRADE_WARNING))))
					.getDistnctDuplicates();
			Set<HKDowngradeWarning> duplicateSet = new HashSet<HKDowngradeWarning>(
					objectList);
			List<HKDowngradeWarning> list = new ArrayList<HKDowngradeWarning>(duplicateSet);

			for (int i = 0; i < list.size(); i++) {
			
				HKDW = list.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKDWProcess.process(HKDW);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKDW = (HKDowngradeWarning) list.get(i);
					HKDW.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = ((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).get(HKDW.getFileName());
					if(fileList.size()>0){
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords()-1);
						((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).update(file);
					}
				}
			}
			
			((HkdwDAO)(DataInputProcessor.getDao(Constants.HK_DOWNGRADE_WARNING))).updateList(list, session);

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
			HKDowngradeWarning HKDW = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_DOWNGRADE_WARNING;
			List<HKDowngradeWarning> objectList = ((HkdwDAO)(DataInputProcessor.getDao(Constants.HK_DOWNGRADE_WARNING)))
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKDW = (HKDowngradeWarning) objectList.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKDW.process(HKDW);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKDW = (HKDowngradeWarning) objectList.get(i);
					HKDW.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			((HkdwDAO)(DataInputProcessor.getDao(Constants.HK_DOWNGRADE_WARNING))).updateList(objectList, session);

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
