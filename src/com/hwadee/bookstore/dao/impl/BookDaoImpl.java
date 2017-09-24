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

	//3.��ע1,2,3���Ƿ�ҳ��ѯ�е��������ķ������Լ������Ĵ���
	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		Page page = new Page<>(cb.getPageNo());
		page.setTotalItemNumber(getTotalBookNumber(cb));
		//��pageʵ���жԷǷ���pageNo������У�飬��������ֻ��Ҫ����page�еķ���У�鼴��
		//�ڵ���getPageList����֮ǰ�޸�cb�д���Ĳ��Ϸ���pageNo
		cb.setPageNo(page.getPageNo());
		page.setList(getPageList(cb, 3));
		return page;
	}

	/*
	 * 1.
	 * �����ݿ��в����������Long�͵ģ�������ǿ��ת��ΪInteger
	 * Integer integer = new Integer(String.valueOf(long));  
	 * ����ʹ�����Ϸ�ʽ������������ת��
	 */
	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "select count(bookId) from book where price >= ? and price <= ?";
		return getForValue(sql, cb.getMinPrice(),cb.getMaxPrice());
	}

	/*
	 * 2.
	 * MySQL��ҳʹ��LIMIT������fromIndex�Ǵ�0��ʼ��
	 * ��ȡ��ǰҳ��ʾ�ļ���
	 */
	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "select * from book where price >= ? and price <= ?"
				+ "limit ?,?";
		/*
		 * ���� ���� CriteriaBook ����ʱ������һ���Ƿ������֣������ݿ��ѯʱ�������Ȼ��һ���Ƿ������֣�
		 * ���Ե�����һ���Ƿ���pageNo,��ѯ��������һ���ռ���
		 */
		return queryForList(sql, cb.getMinPrice(),cb.getMaxPrice(),(cb.getPageNo() - 1)*pageSize,pageSize);
	}

	@Override
	public int getStoreNumber(int bookId) {
		String sql = "select storeNumber from book where bookId = ?"; 
		return getForValue(sql, bookId);
	}

	/*
	 * ��������salesAmount��storeNumber
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
