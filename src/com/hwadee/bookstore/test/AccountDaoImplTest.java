package com.hwadee.bookstore.test;


import org.junit.Test;

import com.hwadee.bookstore.dao.AccountDao;
import com.hwadee.bookstore.dao.impl.AccountDaoImpl;
import com.hwadee.bookstore.domain.Account;
/*
 * ���� AccountDaoImpl �����Ƿ���ȷ
 */
public class AccountDaoImplTest {

	AccountDao accountDao = new  AccountDaoImpl();
	@Test
	public void testGet() {
		Account account = accountDao.get(1);
		System.out.println(account);
	}

	@Test
	public void testUpdateBalance() {
		accountDao.updateBalance(1, 200);
	}

}
