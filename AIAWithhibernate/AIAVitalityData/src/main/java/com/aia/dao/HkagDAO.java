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
		session.update(hkag);
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
	
	public List getAllList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAchieveGold";
		Query query = session.createQuery(hql);
		List<HKAchieveGold> hkagList = query.list();
		tx.commit();
		session.close();
		return hkagList;

	}
	
	
	
	public List getListAsStatus(String status) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAchieveGold where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", status);
		List<HKAchieveGold> hkagList = query.list();
		tx.commit();
		session.close();
		return hkagList;

	}
	
	
	public List getDistnctDuplicates() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAchieveGold where recordStatus= :status and processDate!= :processDate";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_DUPLICATE);
		query.setParameter("processDate", new Date());
		List<HKAchieveGold> hkagList = query.list();
		tx.commit();
		session.close();
		return hkagList;

	}

	public void insertList(Session session, List<HKAchieveGold> objectList,String fileName) {
		int count = 0;
	       
		for (HKAchieveGold hkag : objectList) {
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
		
	}
	public void updateList(List<HKAchieveGold> objectList,Session session) {
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
