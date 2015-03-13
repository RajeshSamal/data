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
import com.aia.dao.HkmyaDAO;
import com.aia.data.DataInputProcessor;
import com.aia.eloqua.process.HKAMCRProcess;
import com.aia.eloqua.process.HKMYAProcess;
import com.aia.model.CDODetails;
import com.aia.model.DataFile;
import com.aia.model.HKMidYearAssessment;
import com.aia.service.AIAService;

public class HKMYASend {

	public static void sendDistinctDuplicateToElqua() {

		SessionFactory sqlSessionFactory = DbConnectionFactory
				.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {

			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKMidYearAssessment HKMYA = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_MID_YEAR_ASSESSMENT;
			List<HKMidYearAssessment> objectList = ((HkmyaDAO)(DataInputProcessor.getDao(Constants.HK_MID_YEAR_ASSESSMENT)))
					.getDistnctDuplicates();
			Set<HKMidYearAssessment> duplicateSet = new HashSet<HKMidYearAssessment>(
					objectList);
			List<HKMidYearAssessment> list = new ArrayList<HKMidYearAssessment>(duplicateSet);

			for (int i = 0; i < list.size(); i++) {
			
				HKMYA = list.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKMYAProcess.process(HKMYA);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < list.size(); i++) {
					HKMYA = (HKMidYearAssessment) list.get(i);
					HKMYA.setRecordStatus(Constants.RECORD_PROCESSED);
					List<DataFile> fileList = ((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).get(HKMYA.getFileName());
					if(fileList.size()>0){
						DataFile file = fileList.get(0);
						file.setDuplicateRecords(file.getDuplicateRecords()-1);
						((DataFileDao)(DataInputProcessor.getDao(Constants.DATAFILE))).update(file);
					}
				}
			}
			
			((HkmyaDAO)(DataInputProcessor.getDao(Constants.HK_MID_YEAR_ASSESSMENT))).updateList(list, session);

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
			HKMidYearAssessment HKMYA = null;
			String fileType = null;
			session = sqlSessionFactory.openSession();
			tx = session.beginTransaction();
			fileType = Constants.HK_MID_YEAR_ASSESSMENT;
			List<HKMidYearAssessment> objectList = ((HkmyaDAO)(DataInputProcessor.getDao(Constants.HK_MID_YEAR_ASSESSMENT)))
					.getListAsStatus(Constants.RECORD_SAVED);

			for (int i = 0; i < objectList.size(); i++) {

				HKMYA = (HKMidYearAssessment) objectList.get(i);
				//comment: below status change may not require.
				//HKAG.setRecordStatus(Constants.RECORD_SENT);
				CDODetails cdoData = HKMYAProcess.process(HKMYA);
				cdoDetailsList.add(cdoData);
			}
			int status = AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			if (status == 0) {
				for (int i = 0; i < objectList.size(); i++) {
					HKMYA = (HKMidYearAssessment) objectList.get(i);
					HKMYA.setRecordStatus(Constants.RECORD_PROCESSED);
				}
			}
			((HkmyaDAO)(DataInputProcessor.getDao(Constants.HK_MID_YEAR_ASSESSMENT))).updateList(objectList, session);

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
