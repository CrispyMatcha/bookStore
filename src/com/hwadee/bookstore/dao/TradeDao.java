package com.hwadee.bookstore.dao;

import java.util.Set;

import com.hwadee.bookstore.domain.Trade;

public interface TradeDao {

	/*
	 * �����ݿ��в��� Trade ����
	 * @param trade
	 */
	void insert(Trade trade);
	
	/*
	 * ���� userId ��ȡ��������� Trade �ļ���
	 * @param userId
	 * @return
	 */
	Set<Trade> getTradesWithUserId(int userId);
}
