package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKAchieveSilver;

public class HkasDAO {

	private SessionFactory sqlSessionFactory;

	public HkasDAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	public void update(HKAchieveSilver hkas) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkas);
		tx.commit();
		session.close();

	}

	public void insert(HKAchieveSilver hkas) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkas);
		tx.commit();
		session.close();

	}
	
	public List getAllList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAchieveSilver";
		Query query = session.createQuery(hql);
		List<HKAchieveSilver> hkasList = query.list();
		tx.commit();
		session.close();
		return hkasList;

	}
	
	
	
	public List getListAsStatus(String status) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAchieveSilver where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", status);
		List<HKAchieveSilver> hkasList = query.list();
		tx.commit();
		session.close();
		return hkasList;

	}
	
	
	public List getDistnctDuplicates() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAchieveSilver where recordStatus= :status and processDate!= :processDate";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_DUPLICATE);
		query.setParameter("processDate", new Date());
		List<HKAchieveSilver> hkasList = query.list();
		tx.commit();
		session.close();
		return hkasList;

	}

	public void insertList(Session session, List<HKAchieveSilver> objectList,String fileName) {
		int count = 0;
	       
		for (HKAchieveSilver hkas : objectList) {
			hkas.setProcessDate(new Date());
			hkas.setLastModifiedDate(new Date());
			hkas.setFileType(Constants.HK_SILVER_ARCHIVE);
			hkas.setFileName(fileName);
			session.save(hkas);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		
	}
	public void updateList(List<HKAchieveSilver> objectList,Session session) {
		int count = 0;

		for (HKAchieveSilver hkas : objectList) {
			hkas.setLastModifiedDate(new Date());
			session.update(hkas);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}

	}


}
