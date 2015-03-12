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
import com.aia.dao.HkamcrDAO;
import com.aia.data.DataInputProcessor;
import com.aia.eloqua.process.HKAMCRProcess;
import com.aia.model.CDODetails;
import com.aia.model.DataFile;
import com.aia.model.HKAdidasMicConRem;
import com.aia.service.AIAService;

public class HKAMCRSend {

	public static void sendDistinctDuplicateToElqua() {

		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKAdidasMicConRem HKAMCR = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_ADIDAS_MICOACH_CONCENT_REMINDER;
			List<HKAdidasMicConRem> objectList = ((HkamcrDAO)(DataInputProcessor.getDao(Constants.HK_ADIDAS_MICOACH_CONCENT_REMINDER)))
					.getDistnctDuplicates();
			Set<HKAdidasMicConRem> duplicateSet = new HashSet<HKAdidasMicConRem>(
					objectList);
			List<HKAdidasMicConRem> list = new ArrayList<HKAdidasMicConRem>(duplicateSet);

			for (int i = 0; i < list.size(); i++) {
			
				HKAMCR = list.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAMCRProcess.process(HKAMCR);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKAMCR = (HKAdidasMicConRem) list.get(i);
					HKAMCR.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = ((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).get(HKAMCR.getFileName());
					if(fileList.size()>0){
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords()-1);
						((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).update(file);
					}
				}
			}
			
			((HkamcrDAO)(DataInputProcessor.getDao(Constants.HK_ADIDAS_MICOACH_CONCENT_REMINDER))).updateList(list, session);

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
			HKAdidasMicConRem HKAMCR = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_ADIDAS_MICOACH_CONCENT_REMINDER;
			List<HKAdidasMicConRem> objectList = ((HkamcrDAO)(DataInputProcessor.getDao(Constants.HK_ADIDAS_MICOACH_CONCENT_REMINDER)))
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKAMCR = (HKAdidasMicConRem) objectList.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKAMCRProcess.process(HKAMCR);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKAMCR = (HKAdidasMicConRem) objectList.get(i);
					HKAMCR.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			((HkamcrDAO)(DataInputProcessor.getDao(Constants.HK_ADIDAS_MICOACH_CONCENT_REMINDER))).updateList(objectList, session);

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
