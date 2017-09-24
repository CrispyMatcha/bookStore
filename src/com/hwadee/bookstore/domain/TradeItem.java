package com.hwadee.bookstore.domain;

//������Ŀʵ����
public class TradeItem {

	private int tradeItemId;

	// ��TradeItem ������Book��bookId
	private int bookId;
	// ����������
	private int quantity;
	private int tradeId;

	// һ��������Ŀֻ���һ���飬���������ж౾
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
