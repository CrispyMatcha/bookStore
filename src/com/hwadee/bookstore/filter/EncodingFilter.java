package com.hwadee.bookstore.filter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class EncodingFilter implements Filter {

	public EncodingFilter() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		// 处理中文
		// post
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		// get
		HttpServletRequest proxyRequst = (HttpServletRequest) Proxy.newProxyInstance(request.getClass().getClassLoader() // 指定当前使用的类加载器
				, new Class[] { HttpServletRequest.class }, // 对目标对象实现的接口类型
				new InvocationHandler()	{ // 事件处理器
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// 获取方法名
						String methodName = method.getName();

						// 定义一个返回值
						Object returnValue = null;
						// getParameter
						if ("getParameter".equals(methodName)) {
							// 获参数
							String value = request.getParameter(args[0].toString());

							// 当前提交的方式
							String methodSubmit = request.getMethod();
							if ("GET".equalsIgnoreCase(methodSubmit)) {
								// 处理中文的乱码
								if (value != null && !"".equals(value)) {
									// 处理乱码
									value = new String(value.getBytes("ISO8859-1"), "utf-8");
								}
							}
							return value;
						} else {
							// 其它的方法我们不处理，继续执行
							returnValue = method.invoke(request, args);
						}
						return returnValue;
					}
				});

		// 传入 是我们上面处理的代理对象
		chain.doFilter(proxyRequst, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
