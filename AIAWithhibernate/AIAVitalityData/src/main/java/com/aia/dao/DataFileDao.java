package com.aia.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.aia.model.DataFile;

public class DataFileDao {

	private SessionFactory sqlSessionFactory;

	public DataFileDao() {
		sqlSessionFactory = DbConnectionFactory.getSessionFactory();
	}

	public void update(DataFile dataFile, Session session) {

		session.save(dataFile);

	}

	public void insert(DataFile dataFile, Session session) {

		session.save(dataFile);

	}

}
