package com.hwadee.bookstore.domain;

import java.util.LinkedHashSet;
import java.util.Set;

/*
 * 登录者信息
 */
public class User {

	private int userId;
	private String userName;
	private int accountId;

	// 一个用户可以有多次交易
	private Set<Trade> trades = new LinkedHashSet<Trade>();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Set<Trade> getTrades() {
		return trades;
	}

	public void setTrades(Set<Trade> trades) {
		this.trades = trades;
	}

}
