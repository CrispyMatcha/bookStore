package com.hwadee.bookstore.test;


import org.junit.Test;

import com.hwadee.bookstore.dao.UserDao;
import com.hwadee.bookstore.dao.impl.UserDaoImpl;
import com.hwadee.bookstore.domain.User;


public class UserDaoImplTest {

	UserDao userDao = new UserDaoImpl();
 	@Test
	public void testGetUser() {
 		User user = userDao.getUser("Ð¡ºì");
 		System.out.println(user.getAccountId());
	}

}
