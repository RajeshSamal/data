package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKAchieveGold;

public class HkagDAO {

	private SessionFactory sqlSessionFactory;

	public HkagDAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	public void update(HKAchieveGold hkag) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkag);
		tx.commit();
		session.close();

	}

	public void insert(HKAchieveGold hkag) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkag);
		tx.commit();
		session.close();

	}
	
	public List getList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAchieveGold where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_SAVED);
		List<HKAchieveGold> hkagList = query.list();
		tx.commit();
		session.close();
		return hkagList;

	}

	public int insertList(Session session, List<HKAchieveGold> objectList,String fileName) {
		int count = 0;
		int duplicate =0;

		
		for (HKAchieveGold hkag : objectList) {
			hkag.setRecordStatus(Constants.RECORD_SAVED);
			hkag.setProcessDate(new Date());
			hkag.setLastModifiedDate(new Date());
			hkag.setFileType(Constants.HK_GOLD_ARCHIVE);
			hkag.setFileName(fileName);
			session.save(hkag);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		return duplicate;
		
	}
	public void updateList(List<HKAchieveGold> objectList,Session session) {
		 session = sqlSessionFactory.openSession();
		int count = 0;

		for (HKAchieveGold hkag : objectList) {
			hkag.setLastModifiedDate(new Date());
			session.update(hkag);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}

	}


}
