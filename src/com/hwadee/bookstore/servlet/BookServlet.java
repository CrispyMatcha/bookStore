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
			// 使用反射动态调用方法
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
		//1.简单验证：验证表单域的值是否符合基本的规范：是否为空，是否可以转为int 类型，是否是一个email，不需要进行查询
		//数据库或者任何的业务方法
		String userName = request.getParameter("userName");
		String accountId = request.getParameter("accountId");
		StringBuffer errors = validateFormField(userName, accountId);
		
		//表单验证通过
		if(errors.toString().equals("")){
			errors = validateUser(userName, accountId);
			
			//用户名和账号验证通过
			if(errors.toString().equals("")){
				errors = validateBookStoreNumber(request);
				
				//库存量验证通过
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
		
		//验证通过，执行具体的逻辑操作
		bookService.cash(BookStoreWebUtils.getShoppingCart(request),userName,accountId);
		response.sendRedirect(request.getContextPath()+"/success.jsp");		
	}
	private AccountService accountService = new AccountServiceImpl();
	//验证余额是否不足
	private StringBuffer validateBalance(HttpServletRequest request,String accountId){
		StringBuffer errors = new StringBuffer("");
		 
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		Account account = accountService.getAccountById(Integer.parseInt(accountId));
		if(shoppingCart.getTotalMoney() > account.getBalance()){
			errors.append("余额不足！");
		}
		return errors;
	}
	
	//验证库存量是否不足
	private StringBuffer validateBookStoreNumber(HttpServletRequest request){
		StringBuffer errors = new StringBuffer("");
		 
		ShoppingCart shoppingCart = BookStoreWebUtils.getShoppingCart(request);
		for(ShoppingCartItem sci : shoppingCart.getItems()){
			int quantity = sci.getQuantity();
			int storeNumber = bookService.getBook(sci.getBook().getBookId()).getStoreNumber();
			
			if(quantity > storeNumber){
				errors.append(sci.getBook().getTitle()+"库存不足<br>");
			}
		}
		return errors;
	}
	//验证用户名与账号是否匹配
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
			errors.append("用户名和账号不匹配");
		}
		return errors;
	}
	
	//验证表单域中是否符合基本的规则:是否为空
	private StringBuffer validateFormField(String userName,String accountId){
		StringBuffer errors = new StringBuffer("");
		if(userName == null || userName.trim().equals("")){
			errors.append("用户名不能为空<br>");
		}
		
		if(accountId == null || accountId.trim().equals("")){
			errors.append("账号不能为空");
		}
		return errors;
	}
	/*
	 * 将servlet中需要直接跳转的方法写成一个总的方法，也就是在servlet中没有任何处理，只是简单地实现跳转而已
	 */
	private void forwardPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");
		// 重定向不可以直接访问web-inf目录下的文件，该目录下的文件不可以直接被访问
		request.getRequestDispatcher("WEB-INF/pages/"+page+".jsp").forward(request, response);
	}
	
	/* * 查看结账信息
	 
	private void toCashPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 重定向不可以直接访问web-inf目录下的文件，该目录下的文件不可以直接被访问
		request.getRequestDispatcher("WEB-INF/pages/cash.jsp").forward(request, response);
	}*/
	private void updateItemQuantity(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 4.在updateItemQuantity 方法中，获取quantity,bookId ,再获取购物车对象，调用service方法做修改
		String bookIdStr = request.getParameter("bookId");
		String quantityStr = request.getParameter("quantity");
		int bookId = -1;
		int quantity = -1;

		//一般我们在转换时不放在一起，因为如果上一个出现异常，下一个不能转换了，而这个我们放在一起
		//因为bookId若转换不成功，后面那个转换没有意义
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		try {
			bookId = Integer.parseInt(bookIdStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (NumberFormatException e) {

		}
		if(bookId > 0 && quantity > 0){
			bookService.updateItemQuantity(sc,bookId,quantity);			
		}
		// 5.传回json 数据：bookNumber :xx ,totalMoney
		Map<String, Object> result = new HashMap<String,Object>();
		result.put("bookNumber", sc.getBookNumber());
		result.put("totalMoney", sc.getTotalMoney());
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		response.setContentType("text/javascript");
		response.getWriter().print(jsonStr);
	}

	/*
	 * 清空购物车
	 */
	private void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clear(sc);
		request.getRequestDispatcher("WEB-INF/pages/emptyCart.jsp").forward(request, response);
	}

	// 删除指定的item信息
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
	 * 查看购物车信息
	 
	private void toCartPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 重定向不可以直接访问web-inf目录下的文件，该目录下的文件不可以直接被访问
		request.getRequestDispatcher("WEB-INF/pages/cart.jsp").forward(request, response);
	}*/

	/*
	 * 添加到购物车
	 */
	private void addToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.获取商品的bookId
		String bookIdStr = request.getParameter("bookId");
		String title = request.getParameter("title");
		int bookId = -1;
		boolean flag = false;
		try {
			bookId = Integer.parseInt(bookIdStr);
		} catch (NumberFormatException e) {
		}

		if (bookId > 0) {
			// 2.获取购物车对象
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			request.setAttribute("bookTitle", title);
			// 3.调用BookService 的 addToCart()方法，把商品放在购物车中
			flag = bookService.addToCart(bookId, sc);
		}

		if (flag) {
			// 4.直接调用getBooks方法返回到以前页面
			getBooks(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
		}
	}

	/*
	 * 获取指定书的详细信息
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
		// 可能在浏览器地址栏中传入一个负数,若书负数，我们不需要到数据库查询，bookId大于0我们才到数据库中查询
		if (bookId > 0) {
			book = bookService.getBook(bookId);
		}
		// 若查询出来的书不存在，则跳转到一个错误页面
		// 因为可以在浏览器地址栏中修改传过去的参数，因此需要判断bookId是否存在
		if (book == null) {
			response.sendRedirect(request.getContextPath() + "/error-1.jsp");
			return;
		}
		request.setAttribute("book", book);

		request.getRequestDispatcher("WEB-INF/pages/book.jsp").forward(request, response);

	}

	/*
	 * 获取每一页的指定pageSize的书的信息
	 */
	private void getBooks(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");

		int pageNo = 1;
		int minPrice = 0;
		int maxPrice = Integer.MAX_VALUE;

		// 这三个的赋值不可以写到一起，如果第一个赋值出现异常，后面的将不能执行
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

		// 封装数据
		CriteriaBook cb = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(cb);

		request.setAttribute("bookPage", page);

		request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request, response);
	}

}