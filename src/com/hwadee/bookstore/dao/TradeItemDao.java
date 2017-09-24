package com.hwadee.bookstore.dao;

import java.util.Collection;
import java.util.Set;

import com.hwadee.bookstore.domain.TradeItem;

public interface TradeItemDao {

	/*
	 * �������� TradeItem ����
	 * @param items
	 */
	void batchSave(Collection<TradeItem> items);
	
	
	/*
	 * ����tradeId ��ȡ��������� TradeItem �ļ���
	 * @param tradeId
	 * @return
	 */
	Set<TradeItem> getTradeItemsWithTradeId(int tradeId);
}
