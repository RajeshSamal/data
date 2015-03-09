package com.aia.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.model.DataFile;
import com.aia.model.HKAchieveGold;

public class DataFileDao {

	private SessionFactory sqlSessionFactory;

	public DataFileDao() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}

	public void update(DataFile dataFile, Session session) {

		session.update(dataFile);

	}

	public void insert(DataFile dataFile, Session session) {

		session.save(dataFile);

	}
	
	public List<DataFile> get(String fileName) {
		Session session =null;
		List<DataFile> fileList=null;
		try{
			session = sqlSessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			String hql = "from DataFile where fielName= :currentFileName";
			Query query = session.createQuery(hql);
			query.setParameter("currentFileName",fileName);
			fileList = query.list();
			tx.commit();
		}
		finally{
			if(session != null){
				session.close();
			}
			
		}
		
		return fileList;

	}
	
	public boolean isFileProcessed(String fileName) {
		boolean exist = false;
		Session session =null;
		try{
			session = sqlSessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			String hql ="from DataFile where fielName= :currentFileName";
			Query query = session.createQuery(hql);
			query.setParameter("currentFileName",fileName);
			List file=query.list();
			tx.commit();
			int i = file.size();
			if(i>0){
				exist=true;
			}
			return exist;
		}
		
		finally{
			if(session != null){
				session.close();
			}
			
		}

	}

}
