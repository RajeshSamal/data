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
import com.aia.dao.HkagDAO;
import com.aia.data.DataInputProcessor;
import com.aia.eloqua.process.HKAGProcess;
import com.aia.model.CDODetails;
import com.aia.model.DataFile;
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
			List<HKAchieveGold> objectList = ((HkagDAO)(DataInputProcessor.getDao(Constants.HK_GOLD_ARCHIVE)))
					.getDistnctDuplicates();
			Set<HKAchieveGold> duplicateSet = new HashSet<HKAchieveGold>(
					objectList);
			List<HKAchieveGold> list = new ArrayList<HKAchieveGold>(duplicateSet);

			for (int i = 0; i < list.size(); i++) {
			
				HKAG = list.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAGProcess.process(HKAG);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKAG = (HKAchieveGold) list.get(i);
					HKAG.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = ((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).get(HKAG.getFileName());
					if(fileList.size()>0){
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords()-1);
						((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).update(file);
					}
				}
			}
			
			((HkagDAO)(DataInputProcessor.getDao(Constants.HK_GOLD_ARCHIVE))).updateList(list, session);

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
			List<HKAchieveGold> objectList = ((HkagDAO)(DataInputProcessor.getDao(Constants.HK_GOLD_ARCHIVE)))
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKAG = (HKAchieveGold) objectList.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAGProcess.process(HKAG);
				cdoDetailsList.add(cdoData);
			}

			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKAG = (HKAchieveGold) objectList.get(i);
					HKAG.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			((HkagDAO)(DataInputProcessor.getDao(Constants.HK_GOLD_ARCHIVE))).updateList(objectList, session);

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
