package com.hwadee.bookstore.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import com.hwadee.bookstore.dao.AccountDao;
import com.hwadee.bookstore.dao.BookDao;
import com.hwadee.bookstore.dao.TradeDao;
import com.hwadee.bookstore.dao.TradeItemDao;
import com.hwadee.bookstore.dao.UserDao;
import com.hwadee.bookstore.dao.impl.AccountDaoImpl;
import com.hwadee.bookstore.dao.impl.BookDaoImpl;
import com.hwadee.bookstore.dao.impl.TradeDaoImpl;
import com.hwadee.bookstore.dao.impl.TradeItemDaoImpl;
import com.hwadee.bookstore.dao.impl.UserDaoImpl;
import com.hwadee.bookstore.domain.Book;
import com.hwadee.bookstore.domain.ShoppingCart;
import com.hwadee.bookstore.domain.ShoppingCartItem;
import com.hwadee.bookstore.domain.Trade;
import com.hwadee.bookstore.domain.TradeItem;
import com.hwadee.bookstore.service.BookService;
import com.hwadee.bookstore.web.CriteriaBook;
import com.hwadee.bookstore.web.Page;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class BookServiceImpl implements BookService{

	private BookDao bookDao = new BookDaoImpl();
	
	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		return bookDao.getPage(cb);
	}

	@Override
	public Book getBook(int bookId) {
		return bookDao.getBook(bookId);
	}

	@Override
	public Boolean addToCart(int bookId, ShoppingCart sc) {
		Book book = getBook(bookId);
		if(book != null){
			sc.addBook(book);
			return true;
		}
		return false;
	}

	@Override
	public void removeItemFromShoppingCart(int bookId,ShoppingCart sc) {
		sc.removeItem(bookId);
	}

	@Override
	public void clear(ShoppingCart sc) {
		sc.clear();
	}

	@Override
	public void updateItemQuantity(ShoppingCart sc, int bookId, int quantity) {
		sc.updateItemQuantity(bookId, quantity);	
	}

	private AccountDao accountDao = new AccountDaoImpl();
	private UserDao userDao = new UserDaoImpl();
	private TradeDao tradeDao = new TradeDaoImpl();
	private TradeItemDao tradeItemDao = new TradeItemDaoImpl();
 	/*
	 * 执行结账操作
	 */
	@Override
	public void cash(ShoppingCart shoppingCart, String userName, String accountId) {
		//1.更新book 数据表相关记录的 salesAmount 和 storeNumber
		bookDao.batchUpdateStoreNumberAndSalesAmount(shoppingCart.getItems());
		
		int i = 10/0;
		//2.更新account 数据表的balance
		accountDao.updateBalance(Integer.parseInt(accountId),shoppingCart.getTotalMoney());
		//3.向 trade 数据表中插入一条记录
		Trade trade = new Trade();
		trade.setTradeTime(new Date(new java.util.Date().getTime()));
		trade.setUserId(userDao.getUser(userName).getUserId());
		tradeDao.insert(trade);
		
		//4.向 tradeitem 数据表中插入n条记录
		Collection<TradeItem> items = new ArrayList<TradeItem>();
		for(ShoppingCartItem sci : shoppingCart.getItems()){
			TradeItem tradeItem = new TradeItem();
			
			tradeItem.setBookId(sci.getBook().getBookId());
			tradeItem.setQuantity(sci.getQuantity());
			tradeItem.setTradeId(trade.getTradeId());
			
			items.add(tradeItem);
		}
		tradeItemDao.batchSave(items);
		
		//5.清空购物车
		shoppingCart.clear();
	}
	
}
