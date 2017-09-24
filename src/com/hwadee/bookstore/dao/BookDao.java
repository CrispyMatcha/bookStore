package com.hwadee.bookstore.dao;
/*
 * 操作书实体的数据处理层
 */

import java.util.Collection;
import java.util.List;

import com.hwadee.bookstore.domain.Book;
import com.hwadee.bookstore.domain.ShoppingCartItem;
import com.hwadee.bookstore.web.CriteriaBook;
import com.hwadee.bookstore.web.Page;

public interface BookDao {

	/*
	 * 根据Id获取指定的书对象
	 * @param id
	 * @return
	 */
	Book getBook(int bookId);
	
	/*
	 * 根据传入的 CriteriaBook 对象返回对应的 Page对象
	 * @param cb
	 */
	Page<Book> getPage(CriteriaBook cb);
	
	/*
	 * 根据对应的 CriteriaBook 对象返回其对应的记录数
	 * @param cb
	 * @return
	 */
	long getTotalBookNumber(CriteriaBook cb);
	
	/*
	 * 根据传入的 CriteriaBook 和 pageSize 返回当前页对应的List
	 * @param cb
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<Book> getPageList(CriteriaBook book ,int pageSize);
	
	/*
	 * 返回指定 id 的 Book 的 storeNumber 的值
	 * @param id
	 * @return
	 */
	int getStoreNumber(int bookId);
	
	/*
	 * 根据传入的 ShoppingCartItem 的集合,
	 * 批量更新  book 数据表中的 storeNumber 和 salesAmount
	 * @param items 
	 */
	void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items);
	
	
}
