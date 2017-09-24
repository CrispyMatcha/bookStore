package com.hwadee.bookstore.service;

import com.hwadee.bookstore.domain.User;

public interface UserService {

	/*
	 * 通过指定的用户名查询用户对象
	 */
	User getUserByUserName(String userName);
	/*
	 * 获取user对象，封装了user中trades的信息
	 */
	User getUserWithTrades(String userName);
}
