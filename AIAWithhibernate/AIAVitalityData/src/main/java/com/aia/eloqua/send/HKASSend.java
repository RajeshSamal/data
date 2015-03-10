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
import com.aia.eloqua.process.HKASProcess;
import com.aia.model.CDODetails;
import com.aia.model.DataFile;
import com.aia.model.HKAchievePlatinum;
import com.aia.model.HKAchieveSilver;
import com.aia.service.AIAService;

public class HKASSend {

	public static void sendDistinctDuplicateToElqua() {

		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKAchieveSilver HKAS = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_SILVER_ARCHIVE;
			List<HKAchieveSilver> objectList = DataInputProcessor.hkasDAO
					.getDistnctDuplicates();
			Set<HKAchieveSilver> duplicateSet = new HashSet<HKAchieveSilver>(
					objectList);
			List<HKAchieveSilver> list = new ArrayList<HKAchieveSilver>(
					duplicateSet);

			for (int i = 0; i < list.size(); i++) {
				HKAS = list.get(i);
				// HKAS.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKASProcess.processHKAS(HKAS);
				cdoDetailsList.add(cdoData);

			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKAS = (HKAchieveSilver) list.get(i);
					HKAS.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = DataInputProcessor.fileDAO
							.get(HKAS.getFileName());
					if (fileList.size() > 0) {
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords() - 1);
						DataInputProcessor.fileDAO.update(file);
					}
				}
			}

			DataInputProcessor.hkasDAO.updateList(list, session);

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
			HKAchieveSilver HKAS = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_SILVER_ARCHIVE;
			List<HKAchieveSilver> objectList = DataInputProcessor.hkasDAO
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKAS = (HKAchieveSilver) objectList.get(i);
				HKAS.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKASProcess.processHKAS(HKAS);
				cdoDetailsList.add(cdoData);
			}

			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKAS = (HKAchieveSilver) objectList.get(i);
					HKAS.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			DataInputProcessor.hkasDAO.updateList(objectList, session);

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
