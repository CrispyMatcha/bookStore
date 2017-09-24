package com.hwadee.bookstore.dao;

import com.hwadee.bookstore.domain.Account;

/*
 * 账户数据处理层
 */
public interface AccountDao {
	/*
	 * 根据 accountId 获取对应的 Account 对象
	 * @param accountId
	 * @return
	 */
	Account get(int accountId);

	/*
	 * 根据传入的 accountId，amount 更新指定账户的余额，扣除amount指定的钱数
	 * @param accountId
	 * @param amount 
	 */
	void updateBalance(int accountId,float amount);
	
}
