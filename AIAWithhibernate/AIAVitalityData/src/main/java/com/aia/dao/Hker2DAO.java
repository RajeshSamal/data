package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKEngagementReminder2;

public class Hker2DAO {

	private SessionFactory sqlSessionFactory;

	public Hker2DAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	public void update(HKEngagementReminder2 hker2) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(hker2);
		tx.commit();
		session.close();

	}

	public void insert(HKEngagementReminder2 hker2) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hker2);
		tx.commit();
		session.close();

	}
	
	public List getAllList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKEngagementReminder2";
		Query query = session.createQuery(hql);
		List<HKEngagementReminder2> hker2List = query.list();
		tx.commit();
		session.close();
		return hker2List;

	}
	
	
	
	public List getListAsStatus(String status) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKEngagementReminder2 where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", status);
		List<HKEngagementReminder2> hker2List = query.list();
		tx.commit();
		session.close();
		return hker2List;

	}
	
	
	public List getDistnctDuplicates() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKEngagementReminder2 where recordStatus= :status and processDate!= :processDate";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_DUPLICATE);
		query.setParameter("processDate", new Date());
		List<HKEngagementReminder2> hker2List = query.list();
		tx.commit();
		session.close();
		return hker2List;

	}

	public void insertList(Session session, List<HKEngagementReminder2> objectList,String fileName) {
		int count = 0;
	       
		for (HKEngagementReminder2 hker2 : objectList) {
			hker2.setProcessDate(new Date());
			hker2.setLastModifiedDate(new Date());
			hker2.setFileType(Constants.HK_ENGAGEMENT_REMINDER2);
			hker2.setFileName(fileName);
			session.save(hker2);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		
	}
	public void updateList(List<HKEngagementReminder2> objectList,Session session) {
		int count = 0;

		for (HKEngagementReminder2 hker2 : objectList) {
			hker2.setLastModifiedDate(new Date());
			session.update(hker2);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}

	}


}
