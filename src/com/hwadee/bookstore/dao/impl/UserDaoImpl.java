package com.hwadee.bookstore.dao.impl;

import com.hwadee.bookstore.dao.UserDao;
import com.hwadee.bookstore.domain.User;

public class UserDaoImpl extends BaseDao<User> implements UserDao{

	@Override
	public User getUser(String userName) {
		String sql = "select userId,userName,accountId from userinfo where userName = ?";
		return query(sql, userName);
	}

}
