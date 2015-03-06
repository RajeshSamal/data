package com.aia.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.dao.DbConnectionFactory;
import com.aia.eloqua.process.HKAGProcess;
import com.aia.model.CDODetails;
import com.aia.model.CommonModel;
import com.aia.model.HKAchieveGold;
import com.aia.service.AIAService;

public class DataOutputProcessor {
	static Logger logger = Logger.getLogger(DataOutputProcessor.class);

	public static void sendToElqua(String key) {
		SessionFactory sqlSessionFactory = DbConnectionFactory.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		try {
			
			Class fileClass = FileToObjectList.getClassFromFile(key);
			List<CDODetails> cdoDetailsList = new ArrayList<CDODetails>();
			HKAchieveGold HKAG = null;
			String fileType = null;
			if (fileClass.getName().equalsIgnoreCase(
					"com.aia.model.HKAchieveGold")) {
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
				DataInputProcessor.hkagDAO.updateList(objectList, session);

			}

			AIAService.syncDataToEloqua(cdoDetailsList, fileType);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
		finally{
			if (session!=null) {
				session.close();
			}
		}

	}

}
