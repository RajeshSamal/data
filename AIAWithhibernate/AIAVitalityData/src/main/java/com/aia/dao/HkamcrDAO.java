package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKAdidasMicConRem;;

public class HkamcrDAO {

	private SessionFactory sqlSessionFactory;

	public HkamcrDAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	public void update(HKAdidasMicConRem hkamcr) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(hkamcr);
		tx.commit();
		session.close();

	}

	public void insert(HKAdidasMicConRem hkamcr) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkamcr);
		tx.commit();
		session.close();

	}
	
	public List getAllList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAdidasMicConRem";
		Query query = session.createQuery(hql);
		List<HKAdidasMicConRem> hkamcrList = query.list();
		tx.commit();
		session.close();
		return hkamcrList;

	}
	
	
	
	public List getListAsStatus(String status) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAdidasMicConRem where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", status);
		List<HKAdidasMicConRem> hkamcrList = query.list();
		tx.commit();
		session.close();
		return hkamcrList;

	}
	
	
	public List getDistnctDuplicates() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKAdidasMicConRem where recordStatus= :status and processDate!= :processDate";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_DUPLICATE);
		query.setParameter("processDate", new Date());
		List<HKAdidasMicConRem> hkamcrList = query.list();
		tx.commit();
		session.close();
		return hkamcrList;

	}

	public void insertList(Session session, List<HKAdidasMicConRem> objectList,String fileName) {
		int count = 0;
	       
		for (HKAdidasMicConRem hkamcr : objectList) {
			hkamcr.setProcessDate(new Date());
			hkamcr.setLastModifiedDate(new Date());
			hkamcr.setFileType(Constants.HK_DOWNGRADE_WARNING);
			hkamcr.setFileName(fileName);
			session.save(hkamcr);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		
	}
	public void updateList(List<HKAdidasMicConRem> objectList,Session session) {
		int count = 0;

		for (HKAdidasMicConRem hkamcr : objectList) {
			hkamcr.setLastModifiedDate(new Date());
			session.update(hkamcr);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}

	}


}
