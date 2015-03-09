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
import com.aia.eloqua.process.HKAGProcess;
import com.aia.model.CDODetails;
import com.aia.model.HKAchieveGold;
import com.aia.service.AIAService;

public class HKAGSend {

	public static void sendDistinctDuplicateToElqua() {

		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKAchieveGold HKAG = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_GOLD_ARCHIVE;
			List<HKAchieveGold> objectList = DataInputProcessor.hkagDAO
					.getDistnctDuplicates();
			Set<HKAchieveGold> duplicateSet = new HashSet<HKAchieveGold>(
					objectList);

			Iterator<HKAchieveGold> iter = duplicateSet.iterator();

			while (iter.hasNext()) {
				HKAG = iter.next();
				HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAGProcess.processHKAG(HKAG);
				cdoDetailsList.add(cdoData);

			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKAG = (HKAchieveGold) objectList.get(i);
					HKAG.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			DataInputProcessor.hkagDAO.updateList(objectList, session);

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
			HKAchieveGold HKAG = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_GOLD_ARCHIVE;
			List<HKAchieveGold> objectList = DataInputProcessor.hkagDAO
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKAG = (HKAchieveGold) objectList.get(i);
				HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAGProcess.processHKAG(HKAG);
				cdoDetailsList.add(cdoData);
			}

			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKAG = (HKAchieveGold) objectList.get(i);
					HKAG.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			DataInputProcessor.hkagDAO.updateList(objectList, session);

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