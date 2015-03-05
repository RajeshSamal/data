package com.aia.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.model.FTP;

public class FTPDao {
	
	private SessionFactory sqlSessionFactory;

	public FTPDao() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}

	
	

	public FTP getFTPDetails(){
		Session session =null;
		try{
			session = sqlSessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			String hql ="from FTP";
			Query query = session.createQuery(hql);
			List file=query.list();
			tx.commit();
			return (FTP)file.get(0);
			
		}
		
		finally{
			if(session != null){
				session.close();
			}
			
		}
	}


}
