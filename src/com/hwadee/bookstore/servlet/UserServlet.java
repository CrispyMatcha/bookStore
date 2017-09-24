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
		// 1.获取userName 请求参数的值
		String userName = request.getParameter("userName");
		// 2.调用UserService
		// 的getUser方法获取User对象，要求User中trades是被装配好的，每一个trade中的tradeItems是装配好的
		User user = userService.getUserWithTrades(userName);
		//3.把User对象放在 request中
		
		if(user == null){
			response.sendRedirect(request.getContextPath()+"/error-1.jsp");
			return;
		}
		request.setAttribute("user", user);
		
		//4.转发页面到 /WEB-INF/pages/trades.jsp
		request.getRequestDispatcher("WEB-INF/pages/trades.jsp").forward(request, response);	
	}

}