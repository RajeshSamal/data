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
import com.aia.dao.HkappDAO;
import com.aia.data.DataInputProcessor;
import com.aia.eloqua.process.HKAPPProcess;
import com.aia.model.CDODetails;
import com.aia.model.DataFile;
import com.aia.model.HKApproachPlatinum;
import com.aia.service.AIAService;

public class HKAPPSend {

	public static void sendDistinctDuplicateToElqua() {

		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKApproachPlatinum HKAPP = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_APPROACH_PLATINUM;
			List<HKApproachPlatinum> objectList = ((HkappDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_PLATINUM)))
					.getDistnctDuplicates();
			Set<HKApproachPlatinum> duplicateSet = new HashSet<HKApproachPlatinum>(
					objectList);
			List<HKApproachPlatinum> list = new ArrayList<HKApproachPlatinum>(duplicateSet);

			for (int i = 0; i < list.size(); i++) {
			
				HKAPP = list.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAPPProcess.process(HKAPP);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKAPP = (HKApproachPlatinum) list.get(i);
					HKAPP.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = ((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).get(HKAPP.getFileName());
					if(fileList.size()>0){
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords()-1);
						((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).update(file);
					}
				}
			}
			
			((HkappDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_PLATINUM))).updateList(list, session);

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
			HKApproachPlatinum HKAPP = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_APPROACH_PLATINUM;
			List<HKApproachPlatinum> objectList = ((HkappDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_PLATINUM)))
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKAPP = (HKApproachPlatinum) objectList.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAPPProcess.process(HKAPP);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKAPP = (HKApproachPlatinum) objectList.get(i);
					HKAPP.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			((HkappDAO)(DataInputProcessor.getDao(Constants.HK_APPROACH_PLATINUM))).updateList(objectList, session);

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
