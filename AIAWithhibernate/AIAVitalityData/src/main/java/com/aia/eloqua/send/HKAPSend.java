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
import com.aia.dao.HkapDAO;
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
			List<HKAchievePlatinum> objectList = ((HkapDAO)(DataInputProcessor.getDao(Constants.HK_PLATINUM_ARCHIVE)))
					.getDistnctDuplicates();
			Set<HKAchievePlatinum> duplicateSet = new HashSet<HKAchievePlatinum>(
					objectList);
			List<HKAchievePlatinum> list = new ArrayList<HKAchievePlatinum>(duplicateSet);


			for (int i = 0; i < list.size(); i++) {
				HKAP = list.get(i);
				//HKAP.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAPProcess.process(HKAP);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKAP = (HKAchievePlatinum) list.get(i);
					HKAP.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = ((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).get(HKAP.getFileName());
					if (fileList.size() > 0) {
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords() - 1);
						((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).update(file);
					}

				}
			}
			
			((HkapDAO)(DataInputProcessor.getDao(Constants.HK_PLATINUM_ARCHIVE))).updateList(list, session);

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
			List<HKAchievePlatinum> objectList = ((HkapDAO)(DataInputProcessor.getDao(Constants.HK_PLATINUM_ARCHIVE)))
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKAP = (HKAchievePlatinum) objectList.get(i);
				//HKAP.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAPProcess.process(HKAP);
				cdoDetailsList.add(cdoData);
			}

			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKAP = (HKAchievePlatinum) objectList.get(i);
					HKAP.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			((HkapDAO)(DataInputProcessor.getDao(Constants.HK_PLATINUM_ARCHIVE))).updateList(objectList, session);

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
