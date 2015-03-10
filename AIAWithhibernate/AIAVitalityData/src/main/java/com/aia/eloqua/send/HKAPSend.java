package com.aia.eloqua.send;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.dao.DbConnectionFactory;
import com.aia.data.DataInputProcessor;
import com.aia.eloqua.process.HKAPProcess;
import com.aia.model.CDODetails;
import com.aia.model.DataFile;
import com.aia.model.HKAchieveGold;
import com.aia.model.HKAchievePlatinum;
import com.aia.service.AIAService;

public class HKAPSend {

	public static void sendDistinctDuplicateToElqua() {

		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKAchievePlatinum HKAP = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_PLATINUM_ARCHIVE;
			List<HKAchievePlatinum> objectList = DataInputProcessor.hkapDAO
					.getDistnctDuplicates();
			Set<HKAchievePlatinum> duplicateSet = new HashSet<HKAchievePlatinum>(
					objectList);
			List<HKAchievePlatinum> list = new ArrayList<HKAchievePlatinum>(duplicateSet);


			for (int i = 0; i < list.size(); i++) {
				HKAP = list.get(i);
				//HKAP.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAPProcess.processHKAP(HKAP);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKAP = (HKAchievePlatinum) list.get(i);
					HKAP.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = DataInputProcessor.fileDAO.get(HKAP.getFileName());
					if (fileList.size() > 0) {
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords() - 1);
						DataInputProcessor.fileDAO.update(file);
					}

				}
			}
			
			DataInputProcessor.hkapDAO.updateList(list, session);

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
			HKAchievePlatinum HKAP = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_PLATINUM_ARCHIVE;
			List<HKAchievePlatinum> objectList = DataInputProcessor.hkapDAO
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKAP = (HKAchievePlatinum) objectList.get(i);
				HKAP.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAPProcess.processHKAP(HKAP);
				cdoDetailsList.add(cdoData);
			}

			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKAP = (HKAchievePlatinum) objectList.get(i);
					HKAP.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			DataInputProcessor.hkapDAO.updateList(objectList, session);

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
