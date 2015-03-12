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
import com.aia.dao.Hker3DAO;
import com.aia.data.DataInputProcessor;
import com.aia.eloqua.process.HKER3Process;
import com.aia.model.CDODetails;
import com.aia.model.DataFile;
import com.aia.model.HKEngagementReminder3;
import com.aia.service.AIAService;

public class HKER3Send {

	public static void sendDistinctDuplicateToElqua() {

		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKEngagementReminder3 HKER3 = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_ENGAGEMENT_REMINDER3;
			List<HKEngagementReminder3> objectList = ((Hker3DAO)(DataInputProcessor.getDao(Constants.HK_ENGAGEMENT_REMINDER3)))
					.getDistnctDuplicates();
			Set<HKEngagementReminder3> duplicateSet = new HashSet<HKEngagementReminder3>(
					objectList);
			List<HKEngagementReminder3> list = new ArrayList<HKEngagementReminder3>(duplicateSet);

			for (int i = 0; i < list.size(); i++) {
			
				HKER3 = list.get(i);
				CDODetails cdoData = HKER3Process.process(HKER3);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKER3 = (HKEngagementReminder3) list.get(i);
					HKER3.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = ((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).get(HKER3.getFileName());
					if(fileList.size()>0){
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords()-1);
						((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).update(file);
					}
				}
			}
			
			((Hker3DAO)(DataInputProcessor.getDao(Constants.HK_ENGAGEMENT_REMINDER3))).updateList(list, session);

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
			HKEngagementReminder3 HKER3 = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_ENGAGEMENT_REMINDER3;
			List<HKEngagementReminder3> objectList = ((Hker3DAO)(DataInputProcessor.getDao(Constants.HK_ENGAGEMENT_REMINDER3)))
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKER3 = (HKEngagementReminder3) objectList.get(i);
				CDODetails cdoData =  HKER3Process.process(HKER3);
				cdoDetailsList.add(cdoData);
			}

			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKER3 = (HKEngagementReminder3) objectList.get(i);
					HKER3.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			((Hker3DAO)(DataInputProcessor.getDao(Constants.HK_ENGAGEMENT_REMINDER3))).updateList(objectList, session);

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
