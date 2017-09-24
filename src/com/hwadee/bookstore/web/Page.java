package com.hwadee.bookstore.web;

import java.util.List;

public class Page<T> {
	// ��ǰ�ڼ�ҳ
	private int pageNo;
	// ��ǰҳ��List
	private List<T> list;
	// ÿҳ��ʾ���ټ�¼
	private int pageSize = 3;
	// ���ж�������¼
	private long totalItemNumber;

	public Page() {

	}

	// ����������Ҫ��pageNo���г�ʼ��
	public Page(int pageNo) {
		super();
		this.pageNo = pageNo;
	}

	//��ҪУ�飬У��ҳ���д��ĵĵ�ǰҳ�Ų��ǺϷ������ݣ��磺������������ҳ������
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

	//��ȡ��ҳ��
	public int getTotalPageNumber(){
	
		int totalPageNumber = (int) (totalItemNumber / pageSize);
		if(totalItemNumber%pageSize!=0){
			totalPageNumber = totalPageNumber + 1;
		}
		return totalPageNumber;
	}
	
	//�ж��Ƿ�����һҳ
	public boolean isHasNext(){
		if(getPageNo() < getTotalPageNumber()){
			return true;
		}
		return false;
	}
	//�ж��Ƿ�����һҳ
	public boolean isHasPrev(){
		if(getPageNo() > 1){
			return true;
		}
		return false;
	}
	
	//������һҳ
	public int getPrevPage(){
		if(isHasPrev()){
			return getPageNo() - 1;
		}
		return pageNo;
	}
	//������һҳ
	public int getNextPage(){
		if(isHasNext()){
			return pageNo + 1;
		}
		return pageNo;
	}
	
}
