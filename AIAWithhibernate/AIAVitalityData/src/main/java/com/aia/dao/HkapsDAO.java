package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKApproachSilver;

public class HkapsDAO {

	private SessionFactory sqlSessionFactory;

	public HkapsDAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	public void update(HKApproachSilver hkass) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(hkass);
		tx.commit();
		session.close();

	}

	public void insert(HKApproachSilver hkass) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkass);
		tx.commit();
		session.close();

	}
	
	public List getAllList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKApproachSilver";
		Query query = session.createQuery(hql);
		List<HKApproachSilver> hkassList = query.list();
		tx.commit();
		session.close();
		return hkassList;

	}
	
	
	
	public List getListAsStatus(String status) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKApproachSilver where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", status);
		List<HKApproachSilver> hkassList = query.list();
		tx.commit();
		session.close();
		return hkassList;

	}
	
	
	public List getDistnctDuplicates() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKApproachSilver where recordStatus= :status and processDate!= :processDate";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_DUPLICATE);
		query.setParameter("processDate", new Date());
		List<HKApproachSilver> hkassList = query.list();
		tx.commit();
		session.close();
		return hkassList;

	}

	public void insertList(Session session, List<HKApproachSilver> objectList,String fileName) {
		int count = 0;
	       
		for (HKApproachSilver hkass : objectList) {
			hkass.setProcessDate(new Date());
			hkass.setLastModifiedDate(new Date());
			hkass.setFileType(Constants.HK_APPROACH_SILVER);
			hkass.setFileName(fileName);
			session.save(hkass);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		
	}
	public void updateList(List<HKApproachSilver> objectList,Session session) {
		int count = 0;

		for (HKApproachSilver hkass : objectList) {
			hkass.setLastModifiedDate(new Date());
			session.update(hkass);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}

	}


}
