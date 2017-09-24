package com.hwadee.bookstore.test;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Set;

import org.junit.Test;

import com.hwadee.bookstore.dao.TradeDao;
import com.hwadee.bookstore.dao.impl.TradeDaoImpl;
import com.hwadee.bookstore.domain.Trade;

public class TradeDaoImplTest {
	private TradeDao tradeDao =  new TradeDaoImpl();
	
	@Test
	public void testInsertTrade() {
		Trade trade = new Trade();
		trade.setUserId(2);
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		tradeDao.insert(trade);
	}

	@Test
	public void testGetTradesWithUserId() {
		Set<Trade> set = tradeDao.getTradesWithUserId(2);
		System.out.println(set);
	}

}
