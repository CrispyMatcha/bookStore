package com.hwadee.bookstore.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ShoppingCart {

	/*
	 * 购物车中可以存在多条购物条目 是一对多的关系
	 */
	private Map<Integer, ShoppingCartItem> books = new HashMap<>();
	
	/*
	 * 修改指定购物项的数量
	 */
	public void updateItemQuantity(int bookId,int quantity){
		ShoppingCartItem shoppingCartItem = books.get(bookId);
		if(shoppingCartItem != null){
			shoppingCartItem.setQuantity(quantity);
		}
	}
	
	/*
	 *  移除指定的购物项
	 *  @param id
	 */
	public void removeItem(int bookId){
		books.remove(bookId);
	}
	
	/*
	 * 清空购物车
	 */
	public void clear(){
		books.clear();
	}
	
	/*
	 * 返回购物车是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return books.isEmpty();
	}
	
	/*
	 * 获取购物车中所有商品的总的钱数
	 */
	public float getTotalMoney(){
		float totalMoney = 0;
		for(ShoppingCartItem sci : getItems()){
			totalMoney += sci.getItemMoney();
		}
		return totalMoney;
	}
	
	/*
	 * 获取购物车中的所有的 ShoppingCartItem 组成的集合
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems(){
		return books.values();
	}
	
	/*
	 * 返回购物车中商品的总数量
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
	 * 检验购物车中是否有 id 指定的商品
	 */
	public boolean hasBook(int bookId){
		return books.containsKey(bookId);
	}
	
	/*
	 * 向购物车中添加一件商品
	 * @param book
	 */
	public void addBook(Book book){
		//1.检查购物车中有没有该商品，若有，则使其数量加1，若没有
		//新创建对应的 ShoppingCartItem ，并把其加入到books中
		
		ShoppingCartItem sci = books.get(book.getBookId());
		if(sci == null){
			sci = new ShoppingCartItem(book);
			books.put(book.getBookId(), sci);
		}else{
			sci.increment();
		}
	}
	
	
	
}
