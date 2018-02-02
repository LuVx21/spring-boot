package org.luvx.dao;

import java.util.List;

import org.luvx.entity.Hello;

public interface HelloDao {

	/**
	 * 查询出Hello 表中的所有记录
	 * @return
	 */
	public List<Hello> findAll();
}
