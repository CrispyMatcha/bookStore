package com.hwadee.bookstore.service;

import com.hwadee.bookstore.domain.Account;

public interface AccountService {
	/*
	 * 根据accountId 查询 account 对象
	 */
	Account getAccountById(int accountId);
}
