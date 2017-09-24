package com.hwadee.bookstore.test;

import java.sql.Date;
import java.util.List;

import org.junit.Test;
import com.hwadee.bookstore.dao.impl.BaseDao;
import com.hwadee.bookstore.dao.impl.BookDaoImpl;
import com.hwadee.bookstore.domain.Book;

/*
 * BaseDao的测试类:全部通过
 */
public class BaseDaoTest {

	private BookDaoImpl bookDao = new BookDaoImpl();
	private BaseDao baseDao = new BaseDao<>();

	@Test
	public void testInsert() {
		String sql = "insert into trade (userId,tradeTime) values (?,?)";
		int id = baseDao.insert(sql, 1, new Date(new java.util.Date().getTime()));
		System.out.println(id);
	}

	@Test
	public void testUpdate() {
		String sql = "update book set SalesAmount = ? where bookId = ?";
		bookDao.update(sql, 10,4);
	}

	@Test
	public void testQuery() {
		String sql ="select * from book where bookId = ?";
		Book book = bookDao.query(sql, 1);
		System.err.println(book);
	}

	@Test
	public void testQueryForList() {
		String sql ="select * from book where bookId < ?";
		List<Book> list = bookDao.queryForList(sql, 4);
		System.out.println(list.toString());
	}

	@Test
	public void testGetForValue() {
		String sql = "select count(bookId) from book";
		long num = bookDao.getForValue(sql);
		System.out.println(num);
		
	}

	@Test
	public void testBatch() {
		String sql = "update book set salesAmount=?,storeNumber=?"
				+ " where bookId = ?";
		bookDao.batch(sql, new Object[]{1,1,1},new Object[]{2,2,2},new Object[]{4,5,3});
	}

}
