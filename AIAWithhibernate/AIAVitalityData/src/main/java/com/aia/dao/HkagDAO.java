package com.aia.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.common.utils.Constants;
import com.aia.model.HKAchieveGold;

public class HkagDAO {

	private SessionFactory sqlSessionFactory;

	public HkagDAO() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}

	public void update(HKAchieveGold hkag) {

		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkag);
		tx.commit();
		session.close();

	}

	public void insert(HKAchieveGold hkag) {
		Session session = sqlSessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(hkag);
		tx.commit();
		session.close();

	}

	public void insertList(Session session, List<HKAchieveGold> objectList) {
		int count = 0;

		
		for (HKAchieveGold hkag : objectList) {
			hkag.setRecordStatus(Constants.RECORD_SAVED);
			hkag.setProcessDate(new Date());
			session.save(hkag);
			session.getTransaction().commit();
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		
	}
	public void updateList(List<HKAchieveGold> objectList) {
		Session session = sqlSessionFactory.openSession();
		int count = 0;

		Transaction tx = session.beginTransaction();
		for (HKAchieveGold hkag : objectList) {
			session.update(hkag);
			session.getTransaction().commit();
			if (++count % 50 == 0) {
				session.flush();
				session.clear();

			}
		}
		tx.commit();
		session.close();

	}


}
