package com.hwadee.bookstore.domain;

//交易条目实体类
public class TradeItem {

	private int tradeItemId;

	// 和TradeItem 关联的Book的bookId
	private int bookId;
	// 包含几本书
	private int quantity;
	private int tradeId;

	// 一个交易条目只针对一种书，这个书可以有多本
	private Book book;

	public TradeItem() {
		// TODO Auto-generated constructor stub
	}
	public TradeItem(int bookId, int quantity, int tradeId) {
		super();
		this.bookId = bookId;
		this.quantity = quantity;
		this.tradeId = tradeId;
	}

	public int getTradeItemId() {
		return tradeItemId;
	}

	public void setTradeItemId(int tradeItemId) {
		this.tradeItemId = tradeItemId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTradeId() {
		return tradeId;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public String toString() {
		return "TradeItem [bookId=" + bookId + ", quantity=" + quantity + ", tradeId=" + tradeId + "]";
	}

}
