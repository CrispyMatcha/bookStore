package com.hwadee.bookstore.dao.impl;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import com.hwadee.bookstore.dao.TradeDao;
import com.hwadee.bookstore.domain.Trade;

public class TradeDaoImpl extends BaseDao<Trade> implements TradeDao {

	@Override
	public void insert(Trade trade) {
		String sql = "insert into trade (userId,tradeTime) values (?,?)";
		int tradeId = insert(sql,trade.getUserId(),trade.getTradeTime());
		trade.setTradeId(tradeId);
	}

	@Override
	public Set<Trade> getTradesWithUserId(int userId) {
		String sql = "select tradeId,userId,tradeTime from trade where userId = ? ORDER BY tradeTime DESC";
		return new LinkedHashSet(queryForList(sql, userId));
	}

}
