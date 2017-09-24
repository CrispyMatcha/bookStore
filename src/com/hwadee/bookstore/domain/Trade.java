package com.hwadee.bookstore.domain;

import java.sql.Date;

//���׼�¼ʵ����

import java.util.LinkedHashSet;
import java.util.Set;

public class Trade {
	// Trade�����Ӧ��ID
	private int tradeId;
	// ����ʱ��
	private Date tradeTime;

	// ��Trade������User��UserId
	private int userId;
	// Trade�����Ķ��TradeItem
	private Set<TradeItem> items = new LinkedHashSet<TradeItem>();

	public int getTradeId() {
		return tradeId;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Set<TradeItem> getItems() {
		return items;
	}

	public void setItems(Set<TradeItem> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", tradeTime=" + tradeTime + ", userId=" + userId + "]";
	}

}
