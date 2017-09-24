package com.hwadee.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hwadee.bookstore.dao.TradeItemDao;
import com.hwadee.bookstore.domain.TradeItem;

public class TradeItemDaoImpl extends BaseDao<TradeItem> implements TradeItemDao{

	@Override
	public void batchSave(Collection<TradeItem> items) {
		String sql = "insert into tradeitem (bookId,quantity,tradeId) values (?,?,?)";
		Object[][] params = new Object[items.size()][3];

		List<TradeItem> tradeItems = new ArrayList<TradeItem>(items);
		for(int i = 0;i < items.size();i++){
			params[i][0] = tradeItems.get(i).getBookId();
			params[i][1] = tradeItems.get(i).getQuantity();
			params[i][2] = tradeItems.get(i).getTradeId();
		}
		batch(sql, params);	
	}

	@Override
	public Set<TradeItem> getTradeItemsWithTradeId(int tradeId) {
		String sql = "select tradeItemId,bookId,quantity,tradeId from tradeitem where tradeId = ?";	
		return new HashSet<>(queryForList(sql, tradeId));
	}

}
