package com.aia.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.model.CustomFieldId;
import com.aia.model.CustomObjectId;

public class CustomObjectDao {
	
	private SessionFactory sqlSessionFactory;

	public CustomObjectDao() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}

	
	

	public String getCustomObjectId(String fileType){
		Session session =null;
		try{
			session = sqlSessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			String hql ="from CustomObjectId where fileType= :fileType";
			Query query = session.createQuery(hql);
			query.setParameter("fileType", fileType);
			List file=query.list();
			tx.commit();
			return ((CustomObjectId)file.get(0)).getCustomId();
			
		}
		
		finally{
			if(session != null){
				session.close();
			}
			
		}
	};
	
	public String getCustomFieldId(String fileType, String filedName){
		Session session =null;
		try{
			session = sqlSessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			String hql ="from CustomFieldId where fileType= :fileType and fieldName= :fieldName";
			Query query = session.createQuery(hql);
			query.setParameter("fileType", fileType);
			query.setParameter("fieldName", filedName);
			List file=query.list();
			tx.commit();
			return ((CustomFieldId)file.get(0)).getFieldId();
			
		}
		
		finally{
			if(session != null){
				session.close();
			}
			
		}
	};

}
