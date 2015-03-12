package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKMidYearAssessment;

public class HkmyaDAO {

	private SessionFactory sqlSessionFactory;

	public HkmyaDAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	public void update(HKMidYearAssessment hkmya) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(hkmya);
		tx.commit();
		session.close();

	}

	public void insert(HKMidYearAssessment hkmya) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkmya);
		tx.commit();
		session.close();

	}
	
	public List getAllList() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKMidYearAssessment";
		Query query = session.createQuery(hql);
		List<HKMidYearAssessment> hkmyaList = query.list();
		tx.commit();
		session.close();
		return hkmyaList;

	}
	
	
	
	public List getListAsStatus(String status) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKMidYearAssessment where recordStatus= :status";
		Query query = session.createQuery(hql);
		query.setParameter("status", status);
		List<HKMidYearAssessment> hkmyaList = query.list();
		tx.commit();
		session.close();
		return hkmyaList;

	}
	
	
	public List getDistnctDuplicates() {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from HKMidYearAssessment where recordStatus= :status and processDate!= :processDate";
		Query query = session.createQuery(hql);
		query.setParameter("status", Constants.RECORD_DUPLICATE);
		query.setParameter("processDate", new Date());
		List<HKMidYearAssessment> hkmyaList = query.list();
		tx.commit();
		session.close();
		return hkmyaList;

	}

	public void insertList(Session session, List<HKMidYearAssessment> objectList,String fileName) {
		int count = 0;
	       
		for (HKMidYearAssessment hkmya : objectList) {
			hkmya.setProcessDate(new Date());
			hkmya.setLastModifiedDate(new Date());
			hkmya.setFileType(Constants.HK_MID_YEAR_ASSESSMENT);
			hkmya.setFileName(fileName);
			session.save(hkmya);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		
	}
	public void updateList(List<HKMidYearAssessment> objectList,Session session) {
		int count = 0;

		for (HKMidYearAssessment hkmya : objectList) {
			hkmya.setLastModifiedDate(new Date());
			session.update(hkmya);
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}

	}


}
