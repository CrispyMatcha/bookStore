package com.hwadee.bookstore.service.impl;

import java.util.Set;

import com.hwadee.bookstore.dao.BookDao;
import com.hwadee.bookstore.dao.TradeDao;
import com.hwadee.bookstore.dao.TradeItemDao;
import com.hwadee.bookstore.dao.UserDao;
import com.hwadee.bookstore.dao.impl.BookDaoImpl;
import com.hwadee.bookstore.dao.impl.TradeDaoImpl;
import com.hwadee.bookstore.dao.impl.TradeItemDaoImpl;
import com.hwadee.bookstore.dao.impl.UserDaoImpl;
import com.hwadee.bookstore.domain.Trade;
import com.hwadee.bookstore.domain.TradeItem;
import com.hwadee.bookstore.domain.User;
import com.hwadee.bookstore.service.UserService;

public class UserServiceImpl implements UserService{

	UserDao userDao = new UserDaoImpl();
	@Override
	public User getUserByUserName(String userName) {
		return userDao.getUser(userName);
	}
	
	private TradeDao tradeDao = new TradeDaoImpl();
	private TradeItemDao tradeItemDao = new TradeItemDaoImpl();
	private BookDao bookDao = new BookDaoImpl();
	@Override
	public User getUserWithTrades(String userName) {
		//1.调用userDao 的方法获取 User对象
		User user = userDao.getUser(userName);
		if(user == null){
			return null;
		}
		
		//2.调用TradeDao 的方法获取 Trade 的集合，把其装配为user的属性
		int userId = user.getUserId();
		Set<Trade> trades = tradeDao.getTradesWithUserId(userId);
		
		//3.调用 TradeItemDao 的方法获取每一个Trade的TrdaeItem 集合，并把其装配成Trade的属性
		if(trades != null){
			for(Trade trade :trades){
				int tradeId = trade.getTradeId();
				Set<TradeItem> tradeItems = tradeItemDao.getTradeItemsWithTradeId(tradeId);
				
				if(tradeItems != null){
					for(TradeItem item:tradeItems){
						item.setBook(bookDao.getBook(item.getBookId()));
					}
					trade.setItems(tradeItems);
				}
				
			}
		}
		user.setTrades(trades);	
		return user;
	}

}
