package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKApproachPlatinum;

public class HkappDAO {

	private SessionFactory sqlSessionFactory;

	public HkappDAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	public void update(HKApproachPlatinum hkaps) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(hkaps);
		tx.commit();
		session.close();

	}

	public void insert(HKApproachPlatinum hkaps) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkaps);
		tx.commit();
		session.close();

	}
	
	public List getAllList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKApproachPlatinum";
		Query query = session.createQuery(hql);
		List<HKApproachPlatinum> hkapsList = query.list();
		tx.commit();
		session.close();
		return hkapsList;

	}
	
	
	
	public List getListAsStatus(String status) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKApproachPlatinum where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", status);
		List<HKApproachPlatinum> hkapsList = query.list();
		tx.commit();
		session.close();
		return hkapsList;

	}
	
	
	public List getDistnctDuplicates() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKApproachPlatinum where recordStatus= :status and processDate!= :processDate";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_DUPLICATE);
		query.setParameter("processDate", new Date());
		List<HKApproachPlatinum> hkapsList = query.list();
		tx.commit();
		session.close();
		return hkapsList;

	}

	public void insertList(Session session, List<HKApproachPlatinum> objectList,String fileName) {
		int count = 0;
	       
		for (HKApproachPlatinum hkaps : objectList) {
			hkaps.setProcessDate(new Date());
			hkaps.setLastModifiedDate(new Date());
			hkaps.setFileType(Constants.HK_APPROACH_PLATINUM);
			hkaps.setFileName(fileName);
			session.save(hkaps);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		
	}
	public void updateList(List<HKApproachPlatinum> objectList,Session session) {
		int count = 0;

		for (HKApproachPlatinum hkaps : objectList) {
			hkaps.setLastModifiedDate(new Date());
			session.update(hkaps);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}

	}


}
