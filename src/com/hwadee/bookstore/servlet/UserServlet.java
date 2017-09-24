package com.hwadee.bookstore.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hwadee.bookstore.domain.User;
import com.hwadee.bookstore.service.UserService;
import com.hwadee.bookstore.service.impl.UserServiceImpl;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private UserService userService = new UserServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.��ȡuserName ���������ֵ
		String userName = request.getParameter("userName");
		// 2.����UserService
		// ��getUser������ȡUser����Ҫ��User��trades�Ǳ�װ��õģ�ÿһ��trade�е�tradeItems��װ��õ�
		User user = userService.getUserWithTrades(userName);
		//3.��User������� request��
		
		if(user == null){
			response.sendRedirect(request.getContextPath()+"/error-1.jsp");
			return;
		}
		request.setAttribute("user", user);
		
		//4.ת��ҳ�浽 /WEB-INF/pages/trades.jsp
		request.getRequestDispatcher("WEB-INF/pages/trades.jsp").forward(request, response);	
	}

}