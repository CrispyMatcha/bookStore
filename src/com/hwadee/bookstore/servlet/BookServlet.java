package com.hwadee.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.hwadee.bookstore.domain.Account;
import com.hwadee.bookstore.domain.Book;
import com.hwadee.bookstore.domain.ShoppingCart;
import com.hwadee.bookstore.domain.ShoppingCartItem;
import com.hwadee.bookstore.domain.User;
import com.hwadee.bookstore.service.AccountService;
import com.hwadee.bookstore.service.BookService;
import com.hwadee.bookstore.service.UserService;
import com.hwadee.bookstore.service.impl.AccountServiceImpl;
import com.hwadee.bookstore.service.impl.BookServiceImpl;
import com.hwadee.bookstore.service.impl.UserServiceImpl;
import com.hwadee.bookstore.web.BookStoreWebUtils;
import com.hwadee.bookstore.web.CriteriaBook;
import com.hwadee.bookstore.web.Page;

@WebServlet("/bookServlet")
public class BookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	private BookService bookService = new BookServiceImpl();
	private UserService userService = new UserServiceImpl();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		try {
			// ʹ�÷��䶯̬���÷���
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
					HttpServletResponse.class);
			method.setAccessible(true);
			method.invoke(this, request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.����֤����֤�����ֵ�Ƿ���ϻ����Ĺ淶���Ƿ�Ϊ�գ��Ƿ����תΪint ���ͣ��Ƿ���һ��email������Ҫ���в�ѯ
		//���ݿ�����κε�ҵ�񷽷�
		String userName = request.getParameter("userName");
		String accountId = request.getParameter("accountId");
		StringBuffer errors = validateFormField(userName, accountId);
		
		//����֤ͨ��
		if(errors.toString().equals("")){
			errors = validateUser(userName, accountId);
			
			//�û������˺���֤ͨ��
			if(errors.toString().equals("")){
				errors = validateBookStoreNumber(request);
				
				//�������֤ͨ��
				if(errors.toString().equals("")){
					errors = validateBalance(request,accountId);
				}
				
			}
		}
		
		if(!errors.toString().equals("")){
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/pages/cash.jsp").forward(request, response);
			return;
		}
		
		//��֤ͨ����ִ�о�����߼�����
		bookService.cash(BookStoreWebUtils.getShoppingCart(request),userName,accountId);
		response.sendRedirect(request.getContextPath()+"/success.jsp");		
	}
	private AccountService accountService = new AccountServiceImpl();
	//��֤����Ƿ���
	private StringBuffer validateBalance(HttpServletRequest request,String accountId){
		StringBuffer errors = new StringBuffer("");
		 
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		Account account = accountService.getAccountById(Integer.parseInt(accountId));
		if(shoppingCart.getTotalMoney() > account.getBalance()){
			errors.append("���㣡");
		}
		return errors;
	}
	
	//��֤������Ƿ���
	private StringBuffer validateBookStoreNumber(HttpServletRequest request){
		StringBuffer errors = new StringBuffer("");
		 
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		for(ShoppingCartItem sci : shoppingCart.getItems()){
			int quantity = sci.getQuantity();
			int storeNumber = bookService.getBook(sci.getBook().getBookId()).getStoreNumber();
			
			if(quantity > storeNumber){
				errors.append(sci.getBook().getTitle()+"��治��<br>");
			}
		}
		return errors;
	}
	//��֤�û������˺��Ƿ�ƥ��
	private StringBuffer validateUser(String userName,String accountId){
		boolean flag = false;
		User user = userService.getUserByUserName(userName);
		if(user != null){
			int accountId2 = user.getAccountId();
			if(accountId.trim().equals(""+accountId2)){
				flag = true;
			}
		}
		
		StringBuffer errors = new StringBuffer("");
		if(flag == false){
			errors.append("�û������˺Ų�ƥ��");
		}
		return errors;
	}
	
	//��֤�������Ƿ���ϻ����Ĺ���:�Ƿ�Ϊ��
	private StringBuffer validateFormField(String userName,String accountId){
		StringBuffer errors = new StringBuffer("");
		if(userName == null || userName.trim().equals("")){
			errors.append("�û�������Ϊ��<br>");
		}
		
		if(accountId == null || accountId.trim().equals("")){
			errors.append("�˺Ų���Ϊ��");
		}
		return errors;
	}
	/*
	 * ��servlet����Ҫֱ����ת�ķ���д��һ���ܵķ�����Ҳ������servlet��û���κδ���ֻ�Ǽ򵥵�ʵ����ת����
	 */
	private void forwardPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		// �ض��򲻿���ֱ�ӷ���web-infĿ¼�µ��ļ�����Ŀ¼�µ��ļ�������ֱ�ӱ�����
		request.getRequestDispatcher("WEB-INF/pages/"+page+".jsp").forward(request, response);
	}
	
	/* * �鿴������Ϣ
	 
	private void toCashPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �ض��򲻿���ֱ�ӷ���web-infĿ¼�µ��ļ�����Ŀ¼�µ��ļ�������ֱ�ӱ�����
		request.getRequestDispatcher("WEB-INF/pages/cash.jsp").forward(request, response);
	}*/
	private void updateItemQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 4.��updateItemQuantity �����У���ȡquantity,bookId ,�ٻ�ȡ���ﳵ���󣬵���service�������޸�
		String bookIdStr = request.getParameter("bookId");
		String quantityStr = request.getParameter("quantity");
		int bookId = -1;
		int quantity = -1;

		//һ��������ת��ʱ������һ����Ϊ�����һ�������쳣����һ������ת���ˣ���������Ƿ���һ��
		//��ΪbookId��ת�����ɹ��������Ǹ�ת��û������
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		try {
			bookId = Integer.parseInt(bookIdStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (NumberFormatException e) {

		}
		if(bookId > 0 && quantity > 0){
			bookService.updateItemQuantity(sc,bookId,quantity);			
		}
		// 5.����json ���ݣ�bookNumber :xx ,totalMoney
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("bookNumber", sc.getBookNumber());
		result.put("totalMoney", sc.getTotalMoney());
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}

	/*
	 * ��չ��ﳵ
	 */
	private void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clear(sc);
		request.getRequestDispatcher("WEB-INF/pages/emptyCart.jsp").forward(request, response);
	}

	// ɾ��ָ����item��Ϣ
	private void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bookIdStr = request.getParameter("bookId");
		int bookId = -1;

		try {
			bookId = Integer.parseInt(bookIdStr);
		} catch (NumberFormatException e) {
		}
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.removeItemFromShoppingCart(bookId, sc);
		if (sc.isEmpty()) {
			request.getRequestDispatcher("WEB-INF/pages/emptyCart.jsp").forward(request, response);
			return;
		}

		request.getRequestDispatcher("WEB-INF/pages/cart.jsp").forward(request, response);
	}

	/*
	 * �鿴���ﳵ��Ϣ
	 
	private void toCartPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �ض��򲻿���ֱ�ӷ���web-infĿ¼�µ��ļ�����Ŀ¼�µ��ļ�������ֱ�ӱ�����
		request.getRequestDispatcher("WEB-INF/pages/cart.jsp").forward(request, response);
	}*/

	/*
	 * ��ӵ����ﳵ
	 */
	private void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.��ȡ��Ʒ��bookId
		String bookIdStr = request.getParameter("bookId");
		String title = request.getParameter("title");
		int bookId = -1;
		boolean flag = false;
		try {
			bookId = Integer.parseInt(bookIdStr);
		} catch (NumberFormatException e) {
		}

		if (bookId > 0) {
			// 2.��ȡ���ﳵ����
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			request.setAttribute("bookTitle", title);
			// 3.����BookService �� addToCart()����������Ʒ���ڹ��ﳵ��
			flag = bookService.addToCart(bookId, sc);
		}

		if (flag) {
			// 4.ֱ�ӵ���getBooks�������ص���ǰҳ��
			getBooks(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
		}
	}

	/*
	 * ��ȡָ�������ϸ��Ϣ
	 */
	private void getBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bookIdStr = request.getParameter("bookId");
		int bookId = -1;
		Book book = null;
		try {
			bookId = Integer.parseInt(bookIdStr);
		} catch (NumberFormatException e) {
		}
		// �������������ַ���д���һ������,���鸺�������ǲ���Ҫ�����ݿ��ѯ��bookId����0���ǲŵ����ݿ��в�ѯ
		if (bookId > 0) {
			book = bookService.getBook(bookId);
		}
		// ����ѯ�������鲻���ڣ�����ת��һ������ҳ��
		// ��Ϊ�������������ַ�����޸Ĵ���ȥ�Ĳ����������Ҫ�ж�bookId�Ƿ����
		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}
		request.setAttribute("book", book);

		request.getRequestDispatcher("WEB-INF/pages/book.jsp").forward(request, response);

	}

	/*
	 * ��ȡÿһҳ��ָ��pageSize�������Ϣ
	 */
	private void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");

		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;

		// �������ĸ�ֵ������д��һ�������һ����ֵ�����쳣������Ľ�����ִ��
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}
		try {
			minPrice = Integer.parseInt(minPriceStr);
		} catch (NumberFormatException e) {
		}
		try {
			maxPrice = Integer.parseInt(maxPriceStr);
		} catch (NumberFormatException e) {
		}

		// ��װ����
		CriteriaBook cb = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(cb);

		request.setAttribute("bookPage", page);

		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
	}

}