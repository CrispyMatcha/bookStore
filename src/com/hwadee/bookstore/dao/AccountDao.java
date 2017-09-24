package com.hwadee.bookstore.dao;

import com.hwadee.bookstore.domain.Account;

/*
 * �˻����ݴ����
 */
public interface AccountDao {
	/*
	 * ���� accountId ��ȡ��Ӧ�� Account ����
	 * @param accountId
	 * @return
	 */
	Account get(int accountId);

	/*
	 * ���ݴ���� accountId��amount ����ָ���˻������۳�amountָ����Ǯ��
	 * @param accountId
	 * @param amount 
	 */
	void updateBalance(int accountId,float amount);
	
}
