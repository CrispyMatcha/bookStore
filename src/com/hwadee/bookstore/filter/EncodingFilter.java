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

		// ��������
		// post
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		// get
		HttpServletRequest proxyRequst = (HttpServletRequest) Proxy.newProxyInstance(request.getClass().getClassLoader() // ָ����ǰʹ�õ��������
				, new Class[] { HttpServletRequest.class }, // ��Ŀ�����ʵ�ֵĽӿ�����
				new InvocationHandler()	{ // �¼�������
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// ��ȡ������
						String methodName = method.getName();

						// ����һ������ֵ
						Object returnValue = null;
						// getParameter
						if ("getParameter".equals(methodName)) {
							// �����
							String value = request.getParameter(args[0].toString());

							// ��ǰ�ύ�ķ�ʽ
							String methodSubmit = request.getMethod();
							if ("GET".equalsIgnoreCase(methodSubmit)) {
								// �������ĵ�����
								if (value != null && !"".equals(value)) {
									// ��������
									value = new String(value.getBytes("ISO8859-1"), "utf-8");
								}
							}
							return value;
						} else {
							// �����ķ������ǲ���������ִ��
							returnValue = method.invoke(request, args);
						}
						return returnValue;
					}
				});

		// ���� ���������洦��Ĵ������
		chain.doFilter(proxyRequst, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
