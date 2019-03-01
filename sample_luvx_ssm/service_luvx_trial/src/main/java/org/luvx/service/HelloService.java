package org.luvx.service;

import java.util.List;

import org.luvx.entity.Hello;

public interface HelloService {
/**
 * 查询所有客户列表
 * @return
 */
	public List<Hello> findAll();
}
