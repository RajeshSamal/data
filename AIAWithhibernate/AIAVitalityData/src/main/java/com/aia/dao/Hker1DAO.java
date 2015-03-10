package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKEngagementReminder1;

public class Hker1DAO {

	private SessionFactory sqlSessionFactory;

	public Hker1DAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	public void update(HKEngagementReminder1 hker1) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(hker1);
		tx.commit();
		session.close();

	}

	public void insert(HKEngagementReminder1 hker1) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hker1);
		tx.commit();
		session.close();

	}
	
	public List getAllList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKEngagementReminder1";
		Query query = session.createQuery(hql);
		List<HKEngagementReminder1> hker1List = query.list();
		tx.commit();
		session.close();
		return hker1List;

	}
	
	
	
	public List getListAsStatus(String status) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKEngagementReminder1 where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", status);
		List<HKEngagementReminder1> hker1List = query.list();
		tx.commit();
		session.close();
		return hker1List;

	}
	
	
	public List getDistnctDuplicates() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKEngagementReminder1 where recordStatus= :status and processDate!= :processDate";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_DUPLICATE);
		query.setParameter("processDate", new Date());
		List<HKEngagementReminder1> hker1List = query.list();
		tx.commit();
		session.close();
		return hker1List;

	}

	public void insertList(Session session, List<HKEngagementReminder1> objectList,String fileName) {
		int count = 0;
	       
		for (HKEngagementReminder1 hker1 : objectList) {
			hker1.setProcessDate(new Date());
			hker1.setLastModifiedDate(new Date());
			hker1.setFileType(Constants.HK_ENGAGEMENT_REMINDER1);
			hker1.setFileName(fileName);
			session.save(hker1);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		
	}
	public void updateList(List<HKEngagementReminder1> objectList,Session session) {
		int count = 0;

		for (HKEngagementReminder1 hker1 : objectList) {
			hker1.setLastModifiedDate(new Date());
			session.update(hker1);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}

	}


}
