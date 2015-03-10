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
import com.aia.eloqua.process.HKER1Process;
import com.aia.model.CDODetails;
import com.aia.model.DataFile;
import com.aia.model.HKEngagementReminder1;
import com.aia.service.AIAService;

public class HKER1Send {

	public static void sendDistinctDuplicateToElqua() {

		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKEngagementReminder1 HKER1 = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_ENGAGEMENT_REMINDER1;
			List<HKEngagementReminder1> objectList = DataInputProcessor.hker1DAO
					.getDistnctDuplicates();
			Set<HKEngagementReminder1> duplicateSet = new HashSet<HKEngagementReminder1>(
					objectList);
			List<HKEngagementReminder1> list = new ArrayList<HKEngagementReminder1>(duplicateSet);

			for (int i = 0; i < list.size(); i++) {
			
				HKER1 = list.get(i);
				CDODetails cdoData = HKER1Process.processHKER1(HKER1);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKER1 = (HKEngagementReminder1) list.get(i);
					HKER1.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = DataInputProcessor.fileDAO.get(HKER1.getFileName());
					if(fileList.size()>0){
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords()-1);
						DataInputProcessor.fileDAO.update(file);
					}
				}
			}
			
			DataInputProcessor.hker1DAO.updateList(list, session);

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
			HKEngagementReminder1 HKER1 = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_ENGAGEMENT_REMINDER1;
			List<HKEngagementReminder1> objectList = DataInputProcessor.hker1DAO
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKER1 = (HKEngagementReminder1) objectList.get(i);
				CDODetails cdoData =  HKER1Process.processHKER1(HKER1);
				cdoDetailsList.add(cdoData);
			}

			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKER1 = (HKEngagementReminder1) objectList.get(i);
					HKER1.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			DataInputProcessor.hker1DAO.updateList(objectList, session);

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
