package com.hwadee.bookstore.service;

import com.hwadee.bookstore.domain.User;

public interface UserService {

	/*
	 * ͨ��ָ�����û�����ѯ�û�����
	 */
	User getUserByUserName(String userName);
	/*
	 * ��ȡuser���󣬷�װ��user��trades����Ϣ
	 */
	User getUserWithTrades(String userName);
}
