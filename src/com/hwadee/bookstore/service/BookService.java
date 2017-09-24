package com.hwadee.bookstore.service;

import com.hwadee.bookstore.domain.Book;
import com.hwadee.bookstore.domain.ShoppingCart;
import com.hwadee.bookstore.web.CriteriaBook;
import com.hwadee.bookstore.web.Page;

public interface BookService {
	/*
	 * 根据传入的 CriteriaBook 对象返回对应的 Page对象
	 * @param cb
	 */
	Page<Book> getPage(CriteriaBook cb);
	
	/*
	 * 通过指定的bookId 查询指定图书信息
	 */
	Book getBook(int bookId);
	
	/*
	 * 将商品添加到购物车中
	 */
	Boolean addToCart(int bookId,ShoppingCart sc);
	
	/*
	 * 根据指定bookId删除指定item
	 */
	void removeItemFromShoppingCart(int bookId,ShoppingCart sc);
	
	/*
	 * 清空购物车
	 */
	void clear(ShoppingCart sc);
	
	/*
	 * 查询修改后的购物车总数量
	 */
	void updateItemQuantity(ShoppingCart sc,int bookId,int quantity);

	/*
	 * 执行结账操作
	 */
	void cash(ShoppingCart shoppingCart, String userName, String accountId);
	
}
