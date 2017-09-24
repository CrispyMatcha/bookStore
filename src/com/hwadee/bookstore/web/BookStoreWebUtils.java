package com.hwadee.bookstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hwadee.bookstore.domain.ShoppingCart;

public class BookStoreWebUtils {

	/*
	 * ��ȡ���ﳵ���󣬴�session�л�ȡ����session����û�иö���
	 * �򴴽�һ���µĹ��ﳵ���󣬷ŵ�session�У����У���ֱ�ӷ���
	 */
	public static ShoppingCart getShoppingCart(HttpServletRequest request){
		HttpSession session = request.getSession();
		ShoppingCart sc = (ShoppingCart) session.getAttribute("ShoppingCart");
		
		if(sc == null){
			sc = new ShoppingCart();
			session.setAttribute("ShoppingCart", sc);
		}
		return sc;
	}
}
