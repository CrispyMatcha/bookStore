package com.hwadee.bookstore.dao;
/*
 * ������ʵ������ݴ����
 */

import java.util.Collection;
import java.util.List;

import com.hwadee.bookstore.domain.Book;
import com.hwadee.bookstore.domain.ShoppingCartItem;
import com.hwadee.bookstore.web.CriteriaBook;
import com.hwadee.bookstore.web.Page;

public interface BookDao {

	/*
	 * ����Id��ȡָ���������
	 * @param id
	 * @return
	 */
	Book getBook(int bookId);
	
	/*
	 * ���ݴ���� CriteriaBook ���󷵻ض�Ӧ�� Page����
	 * @param cb
	 */
	Page<Book> getPage(CriteriaBook cb);
	
	/*
	 * ���ݶ�Ӧ�� CriteriaBook ���󷵻����Ӧ�ļ�¼��
	 * @param cb
	 * @return
	 */
	long getTotalBookNumber(CriteriaBook cb);
	
	/*
	 * ���ݴ���� CriteriaBook �� pageSize ���ص�ǰҳ��Ӧ��List
	 * @param cb
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<Book> getPageList(CriteriaBook book ,int pageSize);
	
	/*
	 * ����ָ�� id �� Book �� storeNumber ��ֵ
	 * @param id
	 * @return
	 */
	int getStoreNumber(int bookId);
	
	/*
	 * ���ݴ���� ShoppingCartItem �ļ���,
	 * ��������  book ���ݱ��е� storeNumber �� salesAmount
	 * @param items 
	 */
	void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items);
	
	
}
