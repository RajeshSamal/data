package com.aia.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.model.Eloqua;

public class EloquaDao {
	
	private SessionFactory sqlSessionFactory;

	public EloquaDao() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}
	
	

	public Eloqua getEloquaDetails(){
		Session session =null;
		try{
			session = sqlSessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			String hql ="from Eloqua";
			Query query = session.createQuery(hql);
			List file=query.list();
			tx.commit();
			return (Eloqua)file.get(0);
			
		}
		
		finally{
			if(session != null){
				session.close();
			}
			
		}
	}

}
