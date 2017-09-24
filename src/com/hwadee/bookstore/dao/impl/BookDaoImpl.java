package com.hwadee.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.hwadee.bookstore.dao.BookDao;
import com.hwadee.bookstore.domain.Book;
import com.hwadee.bookstore.domain.ShoppingCartItem;
import com.hwadee.bookstore.web.CriteriaBook;
import com.hwadee.bookstore.web.Page;

public class BookDaoImpl extends BaseDao<Book> implements BookDao{

	@Override
	public Book getBook(int bookId) {
		String sql = "select * from book where bookId = ?";
		return query(sql, bookId);
	}

	//3.标注1,2,3，是分页查询中的三个核心方法，以及创建的次序
	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page page = new Page<>(cb.getPageNo());
		page.setTotalItemNumber(getTotalBookNumber(cb));
		//在page实体中对非法的pageNo进行了校验，这里我们只需要调用page中的方法校验即可
		//在调用getPageList方法之前修改cb中传入的不合法的pageNo
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 3));
		return page;
	}

	/*
	 * 1.
	 * 从数据库中查出的数据是Long型的，不可以强制转换为Integer
	 * Integer integer = new Integer(String.valueOf(long));  
	 * 可以使用以上方式进行数据类型转换
	 */
	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "select count(bookId) from book where price >= ? and price <= ?";
		return getForValue(sql, cb.getMinPrice(),cb.getMaxPrice());
	}

	/*
	 * 2.
	 * MySQL分页使用LIMIT。其中fromIndex是从0开始的
	 * 获取当前页显示的集合
	 */
	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "select * from book where price >= ? and price <= ?"
				+ "limit ?,?";
		/*
		 * 当在 创建 CriteriaBook 对象时，传入一个非法的数字，在数据库查询时传入的仍然是一个非法的数字，
		 * 所以当传入一个非法的pageNo,查询出来的是一个空集合
		 */
		return queryForList(sql, cb.getMinPrice(),cb.getMaxPrice(),(cb.getPageNo() - 1)*pageSize,pageSize);
	}

	@Override
	public int getStoreNumber(int bookId) {
		String sql = "select storeNumber from book where bookId = ?"; 
		return getForValue(sql, bookId);
	}

	/*
	 * 批量更新salesAmount和storeNumber
	 */
	@Override
	public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
		String sql = "update book set salesAmount = salesAmount + ?,storeNumber = storeNumber - ? "
				+ "where bookId = ?";
		Object[][] params = null;
		params = new Object[items.size()][3];
		List<ShoppingCartItem> scis = new ArrayList<ShoppingCartItem>(items);
		for(int i = 0;i < items.size();i++){
			params[i][0] = scis.get(i).getQuantity();
			params[i][1] = scis.get(i).getQuantity();
			params[i][2] = scis.get(i).getBook().getBookId();
		}
		batch(sql, params);
	}

}
