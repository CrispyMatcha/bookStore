package com.hwadee.bookstore.test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.hwadee.bookstore.dao.BookDao;
import com.hwadee.bookstore.dao.impl.BookDaoImpl;
import com.hwadee.bookstore.domain.Book;
import com.hwadee.bookstore.domain.ShoppingCartItem;
import com.hwadee.bookstore.util.JDBCUtils;
import com.hwadee.bookstore.web.ConnectionContext;
import com.hwadee.bookstore.web.CriteriaBook;
import com.hwadee.bookstore.web.Page;
/*
 * BookDaoImpl≤‚ ‘¿‡
 */
public class BookDaoImplTest {

	private BookDao bookDao = new BookDaoImpl();
	@Test 
	public void testGetBook() {
		Connection connection = JDBCUtils.getConnection();
		ConnectionContext.getInstance().bind(connection);
		Book book = bookDao.getBook(5);
		System.out.println(book);
	}

	@Test
	public void testGetPage() {
		CriteriaBook cb = new CriteriaBook(50,60,90);
		Page<Book> page = bookDao.getPage(cb);
		System.out.println(page.getPageNo());
		System.out.println(page.getTotalPageNumber());
		System.out.println(page.getList());
		System.out.println(page.getPrevPage());
		System.out.println(page.getNextPage());
	}

	@Test
	public void testGetStoreNumber() {
		int storeNumber = bookDao.getStoreNumber(5);
		System.out.println(storeNumber);
	}

	@Test
	public void testBatchUpdateStoreNumberAndSalesAmount() {
		Collection<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();
		
		Book book = bookDao.getBook(1);
		ShoppingCartItem sci = new ShoppingCartItem(book);
		sci.setQuantity(10);
		items.add(sci);
		
		book = bookDao.getBook(2);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(11);
		items.add(sci);
		
	    book = bookDao.getBook(3);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(12);
		items.add(sci);
		
		book = bookDao.getBook(4);
		sci = new ShoppingCartItem(book);
		sci.setQuantity(14);
		items.add(sci);
		
		bookDao.batchUpdateStoreNumberAndSalesAmount(items);
	}

}
