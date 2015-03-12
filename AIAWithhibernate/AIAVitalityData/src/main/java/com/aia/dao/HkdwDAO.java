package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKDowngradeWarning;;

public class HkdwDAO {

	private SessionFactory sqlSessionFactory;

	public HkdwDAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	public void update(HKDowngradeWarning hkdw) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(hkdw);
		tx.commit();
		session.close();

	}

	public void insert(HKDowngradeWarning hkdw) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkdw);
		tx.commit();
		session.close();

	}
	
	public List getAllList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKDowngradeWarning";
		Query query = session.createQuery(hql);
		List<HKDowngradeWarning> hkdwList = query.list();
		tx.commit();
		session.close();
		return hkdwList;

	}
	
	
	
	public List getListAsStatus(String status) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKDowngradeWarning where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", status);
		List<HKDowngradeWarning> hkdwList = query.list();
		tx.commit();
		session.close();
		return hkdwList;

	}
	
	
	public List getDistnctDuplicates() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKDowngradeWarning where recordStatus= :status and processDate!= :processDate";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_DUPLICATE);
		query.setParameter("processDate", new Date());
		List<HKDowngradeWarning> hkdwList = query.list();
		tx.commit();
		session.close();
		return hkdwList;

	}

	public void insertList(Session session, List<HKDowngradeWarning> objectList,String fileName) {
		int count = 0;
	       
		for (HKDowngradeWarning hkdw : objectList) {
			hkdw.setProcessDate(new Date());
			hkdw.setLastModifiedDate(new Date());
			hkdw.setFileType(Constants.HK_DOWNGRADE_WARNING);
			hkdw.setFileName(fileName);
			session.save(hkdw);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		
	}
	public void updateList(List<HKDowngradeWarning> objectList,Session session) {
		int count = 0;

		for (HKDowngradeWarning hkdw : objectList) {
			hkdw.setLastModifiedDate(new Date());
			session.update(hkdw);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}

	}


}
