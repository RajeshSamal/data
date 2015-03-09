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

			Iterator<HKAchieveSilver> iter = duplicateSet.iterator();

			while (iter.hasNext()) {
				HKAS = iter.next();
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
