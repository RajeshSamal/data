package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKApproachGold;;

public class HkapgDAO {

	private SessionFactory sqlSessionFactory;

	public HkapgDAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	public void update(HKApproachGold hkapg) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(hkapg);
		tx.commit();
		session.close();

	}

	public void insert(HKApproachGold hkapg) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkapg);
		tx.commit();
		session.close();

	}
	
	public List getAllList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKApproachGold";
		Query query = session.createQuery(hql);
		List<HKApproachGold> hkapgList = query.list();
		tx.commit();
		session.close();
		return hkapgList;

	}
	
	
	
	public List getListAsStatus(String status) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKApproachGold where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", status);
		List<HKApproachGold> hkapgList = query.list();
		tx.commit();
		session.close();
		return hkapgList;

	}
	
	
	public List getDistnctDuplicates() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKApproachGold where recordStatus= :status and processDate!= :processDate";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_DUPLICATE);
		query.setParameter("processDate", new Date());
		List<HKApproachGold> hkapgList = query.list();
		tx.commit();
		session.close();
		return hkapgList;

	}

	public void insertList(Session session, List<HKApproachGold> objectList,String fileName) {
		int count = 0;
	       
		for (HKApproachGold hkapg : objectList) {
			hkapg.setProcessDate(new Date());
			hkapg.setLastModifiedDate(new Date());
			hkapg.setFileType(Constants.HK_APPROACH_GOLD);
			hkapg.setFileName(fileName);
			session.save(hkapg);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		
	}
	public void updateList(List<HKApproachGold> objectList,Session session) {
		int count = 0;

		for (HKApproachGold hkapg : objectList) {
			hkapg.setLastModifiedDate(new Date());
			session.update(hkapg);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}

	}


}
