package com.hwadee.bookstore.service;

import com.hwadee.bookstore.domain.Account;

public interface AccountService {
	/*
	 * ����accountId ��ѯ account ����
	 */
	Account getAccountById(int accountId);
}
