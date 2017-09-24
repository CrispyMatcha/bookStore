package com.hwadee.bookstore.domain;

/*
 * 封装购物车中的商品，包括对商品的引用以及购物车中该商品的数量
 */
public class ShoppingCartItem {

	private Book book;
	private int quantity;

	public ShoppingCartItem(Book book) {
		super();
		this.book = book;
		this.quantity = 1;
	}

	public Book getBook() {
		return book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	//返回该商品在购物车中的钱数
	public float getItemMoney(){
		return book.getPrice() * quantity;
	}
	
	/*
	 * 使商品的数量加1
	 */
	public void increment(){
		quantity++;
	}
}
