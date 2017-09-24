package com.hwadee.bookstore.domain;

import java.sql.Date;

//交易记录实体类

import java.util.LinkedHashSet;
import java.util.Set;

public class Trade {
	// Trade对象对应的ID
	private int tradeId;
	// 交易时间
	private Date tradeTime;

	// 和Trade关联的User的UserId
	private int userId;
	// Trade关联的多个TradeItem
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
