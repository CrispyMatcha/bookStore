package com.hwadee.bookstore.dao;

import java.util.Collection;
import java.util.Set;

import com.hwadee.bookstore.domain.TradeItem;

public interface TradeItemDao {

	/*
	 * 批量保存 TradeItem 对象
	 * @param items
	 */
	void batchSave(Collection<TradeItem> items);
	
	
	/*
	 * 根据tradeId 获取与其关联的 TradeItem 的集合
	 * @param tradeId
	 * @return
	 */
	Set<TradeItem> getTradeItemsWithTradeId(int tradeId);
}
