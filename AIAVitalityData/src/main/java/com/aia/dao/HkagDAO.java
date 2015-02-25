package com.aia.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.aia.model.HKAchieveGold;


 
public class HkagDAO {
	
	private SqlSessionFactory sqlSessionFactory; 
	
	public HkagDAO(){
		sqlSessionFactory = IBatisConnectionFactory.getSqlSessionFactory();
	}
	

	/**
	 * Returns the list of all hkag instances from the database.
	 * @return the list of all hkag instances from the database.
	 */
	@SuppressWarnings("unchecked")
	public List<HKAchieveGold> selectAll(){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			List<HKAchieveGold> list = session.selectList("Hkag.getAll");
			return list;
		} finally {
			session.close();
		}
	}

	/**
	 * Returns a Contact instance from the database.
	 * @param id primary key value used for lookup.
	 * @return A hkag instance with a primary key value equals to pk. null if there is no matching row.
	 */
	public HKAchieveGold selectById(int id){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			HKAchieveGold hkag = (HKAchieveGold) session.selectOne("Hkag.getById",id);
			return hkag;
		} finally {
			session.close();
		}
	}

	/**
	 * Updates an instance of hkag in the database.
	 * @param hkag the instance to be updated.
	 */
	public void update(HKAchieveGold hkag){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			session.update("Hkag.update", hkag);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Insert an instance of Contact into the database.
	 * @param contact the instance to be persisted.
	 */
	public void insert(HKAchieveGold hkag){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			session.insert("Hkag.insert", hkag);
			session.commit();
		} finally {
			session.close();
		}
	}

	/**
	 * Delete an instance of Contact from the database.
	 * @param id primary key value of the instance to be deleted.
	 */
	public void delete(int id){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			session.delete("Hkag.deleteById", id);
			session.commit();
		} finally {
			session.close();
		}
	}
}
