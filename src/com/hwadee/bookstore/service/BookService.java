package com.hwadee.bookstore.service;

import com.hwadee.bookstore.domain.Book;
import com.hwadee.bookstore.domain.ShoppingCart;
import com.hwadee.bookstore.web.CriteriaBook;
import com.hwadee.bookstore.web.Page;

public interface BookService {
	/*
	 * ���ݴ���� CriteriaBook ���󷵻ض�Ӧ�� Page����
	 * @param cb
	 */
	Page<Book> getPage(CriteriaBook cb);
	
	/*
	 * ͨ��ָ����bookId ��ѯָ��ͼ����Ϣ
	 */
	Book getBook(int bookId);
	
	/*
	 * ����Ʒ��ӵ����ﳵ��
	 */
	Boolean addToCart(int bookId,ShoppingCart sc);
	
	/*
	 * ����ָ��bookIdɾ��ָ��item
	 */
	void removeItemFromShoppingCart(int bookId,ShoppingCart sc);
	
	/*
	 * ��չ��ﳵ
	 */
	void clear(ShoppingCart sc);
	
	/*
	 * ��ѯ�޸ĺ�Ĺ��ﳵ������
	 */
	void updateItemQuantity(ShoppingCart sc,int bookId,int quantity);

	/*
	 * ִ�н��˲���
	 */
	void cash(ShoppingCart shoppingCart, String userName, String accountId);
	
}
