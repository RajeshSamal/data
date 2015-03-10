package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKEngagementReminder3;

public class Hker3DAO {

	private SessionFactory sqlSessionFactory;

	public Hker3DAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	public void update(HKEngagementReminder3 hker3) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(hker3);
		tx.commit();
		session.close();

	}

	public void insert(HKEngagementReminder3 hker3) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hker3);
		tx.commit();
		session.close();

	}
	
	public List getAllList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKEngagementReminder3";
		Query query = session.createQuery(hql);
		List<HKEngagementReminder3> hker3List = query.list();
		tx.commit();
		session.close();
		return hker3List;

	}
	
	
	
	public List getListAsStatus(String status) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKEngagementReminder3 where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", status);
		List<HKEngagementReminder3> hker3List = query.list();
		tx.commit();
		session.close();
		return hker3List;

	}
	
	
	public List getDistnctDuplicates() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKEngagementReminder3 where recordStatus= :status and processDate!= :processDate";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_DUPLICATE);
		query.setParameter("processDate", new Date());
		List<HKEngagementReminder3> hker3List = query.list();
		tx.commit();
		session.close();
		return hker3List;

	}

	public void insertList(Session session, List<HKEngagementReminder3> objectList,String fileName) {
		int count = 0;
	       
		for (HKEngagementReminder3 hker3 : objectList) {
			hker3.setProcessDate(new Date());
			hker3.setLastModifiedDate(new Date());
			hker3.setFileType(Constants.HK_ENGAGEMENT_REMINDER3);
			hker3.setFileName(fileName);
			session.save(hker3);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		
	}
	public void updateList(List<HKEngagementReminder3> objectList,Session session) {
		int count = 0;

		for (HKEngagementReminder3 hker3 : objectList) {
			hker3.setLastModifiedDate(new Date());
			session.update(hker3);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}

	}


}
