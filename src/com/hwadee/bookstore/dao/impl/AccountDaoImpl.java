package com.hwadee.bookstore.dao.impl;

import com.hwadee.bookstore.dao.AccountDao;
import com.hwadee.bookstore.domain.Account;

public class AccountDaoImpl extends BaseDao<Account> implements AccountDao{

	@Override
	public Account get(int accountId) {
		String sql = "select accountId,balance from account where accountId = ?";
		return query(sql, accountId);
	}

	@Override
	public void updateBalance(int accountId, float amount) {
		String sql = "update account set balance = balance - ? where accountId = ?";
		update(sql, amount,accountId);
	}

}
