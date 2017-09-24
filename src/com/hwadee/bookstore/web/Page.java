package com.hwadee.bookstore.web;

import java.util.List;

public class Page<T> {
	// 当前第几页
	private int pageNo;
	// 当前页的List
	private List<T> list;
	// 每页显示多少记录
	private int pageSize = 3;
	// 共有多少条记录
	private long totalItemNumber;

	public Page() {

	}

	// 构造器中需要对pageNo进行初始化
	public Page(int pageNo) {
		super();
		this.pageNo = pageNo;
	}

	//需要校验，校验页面中传的的当前页号不是合法的数据，如：负数，大于总页数的数
	public int getPageNo() {
		if(pageNo < 0){
			pageNo = 1;
		}
		if(pageNo > getTotalPageNumber()){
			pageNo = getTotalPageNumber();
		}
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}

	//获取总页数
	public int getTotalPageNumber(){
	
		int totalPageNumber = (int) (totalItemNumber / pageSize);
		if(totalItemNumber%pageSize!=0){
			totalPageNumber = totalPageNumber + 1;
		}
		return totalPageNumber;
	}
	
	//判断是否有下一页
	public boolean isHasNext(){
		if(getPageNo() < getTotalPageNumber()){
			return true;
		}
		return false;
	}
	//判断是否有上一页
	public boolean isHasPrev(){
		if(getPageNo() > 1){
			return true;
		}
		return false;
	}
	
	//返回上一页
	public int getPrevPage(){
		if(isHasPrev()){
			return getPageNo() - 1;
		}
		return pageNo;
	}
	//返回下一页
	public int getNextPage(){
		if(isHasNext()){
			return pageNo + 1;
		}
		return pageNo;
	}
	
}
