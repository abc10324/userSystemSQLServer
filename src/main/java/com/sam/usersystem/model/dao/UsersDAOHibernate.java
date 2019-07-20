package com.sam.usersystem.model.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sam.usersystem.model.UsersBean;

@Repository
public class UsersDAOHibernate implements UsersDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private final String SELECT_BY_ID = "from com.sam.usersystem.model.UsersBean a where a.userID = :userID";
	private final String SELECT_BY_NAME = "from com.sam.usersystem.model.UsersBean a where a.userName = :userName";
	private final String SELECT_LIKE_NAME = "select a.userName from com.sam.usersystem.model.UsersBean a where a.userName like :userName";
	
	@Override
	public UsersBean insert(UsersBean bean) {
		
		try {
			sessionFactory.getCurrentSession().save(bean);
		} catch (ConstraintViolationException e) {
//			e.printStackTrace();
			return null;
		}
		
		return bean;
	}

	@Override
	public UsersBean selectById(String userID) {
		UsersBean result = null;
		
		try {
			result = (UsersBean)sessionFactory.getCurrentSession()
											  .createQuery(SELECT_BY_ID)
										  	  .setParameter("userID", userID)
											  .getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return result;
	}

	@Override
	public UsersBean selectByName(String userName) {
		UsersBean result = null;
		
		try {
			result = (UsersBean)sessionFactory.getCurrentSession()
											  .createQuery(SELECT_BY_NAME)
										  	  .setParameter("userName", userName)
											  .getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		return result;
	}

	@Override
	public List<String> selectLikeName(String userName) {
		return sessionFactory.getCurrentSession()
							 .createQuery(SELECT_LIKE_NAME)
							 .setParameter("userName", userName + "%")
							 .getResultList();
		
	}

}
