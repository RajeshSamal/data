package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKAchievePlatinum;;

public class HkapDAO {

	private SessionFactory sqlSessionFactory;

	public HkapDAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	public void update(HKAchievePlatinum hkap) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkap);
		tx.commit();
		session.close();

	}

	public void insert(HKAchievePlatinum hkap) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkap);
		tx.commit();
		session.close();

	}
	
	public List getAllList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAchievePlatinum";
		Query query = session.createQuery(hql);
		List<HKAchievePlatinum> hkapList = query.list();
		tx.commit();
		session.close();
		return hkapList;

	}
	
	
	
	public List getListAsStatus(String status) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAchievePlatinum where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", status);
		List<HKAchievePlatinum> hkapList = query.list();
		tx.commit();
		session.close();
		return hkapList;

	}
	
	
	public List getDistnctDuplicates() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAchievePlatinum where recordStatus= :status and processDate!= :processDate";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_DUPLICATE);
		query.setParameter("processDate", new Date());
		List<HKAchievePlatinum> hkapList = query.list();
		tx.commit();
		session.close();
		return hkapList;

	}

	public void insertList(Session session, List<HKAchievePlatinum> objectList,String fileName) {
		int count = 0;
	       
		for (HKAchievePlatinum hkap : objectList) {
			hkap.setProcessDate(new Date());
			hkap.setLastModifiedDate(new Date());
			hkap.setFileType(Constants.HK_PLATINUM_ARCHIVE);
			hkap.setFileName(fileName);
			session.save(hkap);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		
	}
	public void updateList(List<HKAchievePlatinum> objectList,Session session) {
		int count = 0;

		for (HKAchievePlatinum hkap : objectList) {
			hkap.setLastModifiedDate(new Date());
			session.update(hkap);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}

	}


}
