package com.hwadee.bookstore.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ShoppingCart {

	/*
	 * ���ﳵ�п��Դ��ڶ���������Ŀ ��һ�Զ�Ĺ�ϵ
	 */
	private Map<Integer, ShoppingCartItem> books = new HashMap<>();
	
	/*
	 * �޸�ָ�������������
	 */
	public void updateItemQuantity(int bookId,int quantity){
		ShoppingCartItem shoppingCartItem = books.get(bookId);
		if(shoppingCartItem != null){
			shoppingCartItem.setQuantity(quantity);
		}
	}
	
	/*
	 *  �Ƴ�ָ���Ĺ�����
	 *  @param id
	 */
	public void removeItem(int bookId){
		books.remove(bookId);
	}
	
	/*
	 * ��չ��ﳵ
	 */
	public void clear(){
		books.clear();
	}
	
	/*
	 * ���ع��ﳵ�Ƿ�Ϊ��
	 * @return
	 */
	public boolean isEmpty(){
		return books.isEmpty();
	}
	
	/*
	 * ��ȡ���ﳵ��������Ʒ���ܵ�Ǯ��
	 */
	public float getTotalMoney(){
		float totalMoney = 0;
		for(ShoppingCartItem sci : getItems()){
			totalMoney += sci.getItemMoney();
		}
		return totalMoney;
	}
	
	/*
	 * ��ȡ���ﳵ�е����е� ShoppingCartItem ��ɵļ���
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems(){
		return books.values();
	}
	
	/*
	 * ���ع��ﳵ����Ʒ��������
	 * @return
	 */
	public int getBookNumber(){
		int totalNumber = 0;
		for(ShoppingCartItem sci: books.values()){
			totalNumber += sci.getQuantity();
		}
		return totalNumber;
	}
	
	public Map<Integer, ShoppingCartItem> getBooks(){
		return books;
	}
	
	/*
	 * ���鹺�ﳵ���Ƿ��� id ָ������Ʒ
	 */
	public boolean hasBook(int bookId){
		return books.containsKey(bookId);
	}
	
	/*
	 * ���ﳵ�����һ����Ʒ
	 * @param book
	 */
	public void addBook(Book book){
		//1.��鹺�ﳵ����û�и���Ʒ�����У���ʹ��������1����û��
		//�´�����Ӧ�� ShoppingCartItem ����������뵽books��
		
		ShoppingCartItem sci = books.get(book.getBookId());
		if(sci == null){
			sci = new ShoppingCartItem(book);
			books.put(book.getBookId(), sci);
		}else{
			sci.increment();
		}
	}
	
	
	
}
