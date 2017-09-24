package com.hwadee.bookstore.test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import com.hwadee.bookstore.dao.TradeItemDao;
import com.hwadee.bookstore.dao.impl.TradeItemDaoImpl;
import com.hwadee.bookstore.domain.TradeItem;

public class TradeItemDaoImplTest {

	private TradeItemDao tradeItemDao = new TradeItemDaoImpl();
	@Test
	public void testBatchSave() {
		Collection<TradeItem> items = new ArrayList<>();
		items.add(new TradeItem(1, 10, 2));
		items.add(new TradeItem(2, 20, 2));
		items.add(new TradeItem(3, 30, 2));
		items.add(new TradeItem(4, 40, 2));
		items.add(new TradeItem(5, 50, 2));

		tradeItemDao.batchSave(items);
	}

	@Test
	public void testGetTradeItemsWithTradeId() {
		Set<TradeItem> items = tradeItemDao.getTradeItemsWithTradeId(2);
		System.out.println(items);
	}

}
