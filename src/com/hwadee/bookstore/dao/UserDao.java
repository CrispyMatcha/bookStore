package com.hwadee.bookstore.dao;

import com.hwadee.bookstore.domain.User;

public interface UserDao {

	/*
	 * 根据用户名获取User对象
	 * @param username
	 * @return
	 */
	User getUser(String userName);
}
