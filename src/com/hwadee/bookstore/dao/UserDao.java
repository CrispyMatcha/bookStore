package com.hwadee.bookstore.dao;

import com.hwadee.bookstore.domain.User;

public interface UserDao {

	/*
	 * �����û�����ȡUser����
	 * @param username
	 * @return
	 */
	User getUser(String userName);
}
