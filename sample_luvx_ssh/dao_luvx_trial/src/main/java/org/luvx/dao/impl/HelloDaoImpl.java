package org.luvx.dao.impl;

import java.util.List;

import org.luvx.entity.Hello;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import org.luvx.dao.HelloDao;

/**
 * Hibernate用
 */
public class HelloDaoImpl extends HibernateDaoSupport implements HelloDao {

	public List<Hello> findAll() {
		return (List<Hello>)this.getHibernateTemplate().find("from Hello");
	}

}
