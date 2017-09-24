package com.hwadee.bookstore.dao;

import java.util.Set;

import com.hwadee.bookstore.domain.Trade;

public interface TradeDao {

	/*
	 * 向数据库中插入 Trade 对象
	 * @param trade
	 */
	void insert(Trade trade);
	
	/*
	 * 根据 userId 获取和其关联的 Trade 的集合
	 * @param userId
	 * @return
	 */
	Set<Trade> getTradesWithUserId(int userId);
}
