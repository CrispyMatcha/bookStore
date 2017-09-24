package com.hwadee.bookstore.service.impl;

import com.hwadee.bookstore.dao.AccountDao;
import com.hwadee.bookstore.dao.impl.AccountDaoImpl;
import com.hwadee.bookstore.domain.Account;
import com.hwadee.bookstore.service.AccountService;

public class AccountServiceImpl implements AccountService{

	private AccountDao accountDao = new AccountDaoImpl();
	@Override
	public Account getAccountById(int accountId) {
		return accountDao.get(accountId);
	}

}
