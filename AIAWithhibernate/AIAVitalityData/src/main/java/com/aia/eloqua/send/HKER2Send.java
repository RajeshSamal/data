package com.aia.eloqua.send;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.dao.DbConnectionFactory;
import com.aia.data.DataInputProcessor;
import com.aia.eloqua.process.HKER2Process;
import com.aia.model.CDODetails;
import com.aia.model.DataFile;
import com.aia.model.HKEngagementReminder2;
import com.aia.service.AIAService;

public class HKER2Send {

	public static void sendDistinctDuplicateToElqua() {

		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKEngagementReminder2 HKER2 = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_ENGAGEMENT_REMINDER2;
			List<HKEngagementReminder2> objectList = DataInputProcessor.hker2DAO
					.getDistnctDuplicates();
			Set<HKEngagementReminder2> duplicateSet = new HashSet<HKEngagementReminder2>(
					objectList);
			List<HKEngagementReminder2> list = new ArrayList<HKEngagementReminder2>(duplicateSet);

			for (int i = 0; i < list.size(); i++) {
			
				HKER2 = list.get(i);
				CDODetails cdoData = HKER2Process.processHKER2(HKER2);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKER2 = (HKEngagementReminder2) list.get(i);
					HKER2.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = DataInputProcessor.fileDAO.get(HKER2.getFileName());
					if(fileList.size()>0){
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords()-1);
						DataInputProcessor.fileDAO.update(file);
					}
				}
			}
			
			DataInputProcessor.hker2DAO.updateList(list, session);

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
			HKEngagementReminder2 HKER2 = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_ENGAGEMENT_REMINDER1;
			List<HKEngagementReminder2> objectList = DataInputProcessor.hker1DAO
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKER2 = (HKEngagementReminder2) objectList.get(i);
				CDODetails cdoData =  HKER2Process.processHKER2(HKER2);
				cdoDetailsList.add(cdoData);
			}

			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKER2 = (HKEngagementReminder2) objectList.get(i);
					HKER2.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			DataInputProcessor.hker2DAO.updateList(objectList, session);

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
