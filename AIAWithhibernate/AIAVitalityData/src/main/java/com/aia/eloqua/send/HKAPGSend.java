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
import com.aia.dao.HkapgDAO;
import com.aia.data.DataInputProcessor;
import com.aia.eloqua.process.HKAGProcess;
import com.aia.eloqua.process.HKAPGProcess;
import com.aia.model.CDODetails;
import com.aia.model.DataFile;
import com.aia.model.HKApproachGold;
import com.aia.service.AIAService;

public class HKAPGSend {

	public static void sendDistinctDuplicateToElqua() {

		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKApproachGold HKAPG = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_APPROACH_GOLD;
			List<HKApproachGold> objectList = ((HkapgDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_GOLD)))
					.getDistnctDuplicates();
			Set<HKApproachGold> duplicateSet = new HashSet<HKApproachGold>(
					objectList);
			List<HKApproachGold> list = new ArrayList<HKApproachGold>(duplicateSet);

			for (int i = 0; i < list.size(); i++) {
			
				HKAPG = list.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAPGProcess.processHKAG(HKAPG);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKAPG = (HKApproachGold) list.get(i);
					HKAPG.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = ((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).get(HKAPG.getFileName());
					if(fileList.size()>0){
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords()-1);
						((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).update(file);
					}
				}
			}
			
			((HkapgDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_GOLD))).updateList(list, session);

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
			HKApproachGold HKAPG = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_APPROACH_GOLD;
			List<HKApproachGold> objectList = ((HkapgDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_GOLD)))
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKAPG = (HKApproachGold) objectList.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAPGProcess.processHKAG(HKAPG);
				cdoDetailsList.add(cdoData);
			}

			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKAPG = (HKApproachGold) objectList.get(i);
					HKAPG.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			((HkapgDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_GOLD))).updateList(objectList, session);

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
