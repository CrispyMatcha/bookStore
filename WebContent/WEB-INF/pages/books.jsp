<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    response.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>

<!-- 给所有的a标签都加上hidden隐藏的条件 -->
<script type="text/javascript">
	$(function(){
		
		$("#pageNo").change(function(){
			var val = $(this).val();
			val = $.trim(val);
			
			//1.校验 val 是否为数字1,2，而不是a12,f等不合法的输入
			var reg = /^\d+$/g;
			if(!reg.test(val)){
				alert("输入的不是合法的页码");
				$(this).val("");
				return;
			}
			
			//2.校验var在一个合法的范围内 1~totalPageNumber
			var pageNo = parseInt(val);
			if(pageNo < 1 || pageNo > "${bookPage.totalPageNumber }"){
				alert("输入的不是合法的页码");
				$(this).val("");
				return;
			}
			
			//页面跳转
			var href = "bookServlet?method=getBooks&pageNo=" + pageNo + "&" + $(":hidden").serialize();
			window.location.href = href;	
		});
		
		
	})
</script>
<%@ include file="/commons/queryCondition.jsp" %>
</head>
<body>

	<center>
	
		<c:if test="${!empty requestScope.bookTitle }">
			您已经将${requestScope.bookTitle}放入购物车中
		</c:if>
		<br><br>
		<c:if test="${!empty sessionScope.ShoppingCart.books }">
			您的购物车中有${sessionScope.ShoppingCart.bookNumber }件商品，<a href="bookServlet?method=forwardPage&page=cart&pageNo=${bookPage.pageNo }">查看购物车</a>
		</c:if>
		
		<br> <br>
		<form action="bookServlet?method=getBooks" method="post">
			Price:<input type="text" size="2" name="minPrice" /> - <input type="text"
				size="2" name="maxPrice" /> <input type="submit" value="查询" />
		</form>

		<br> <br>
		<table cellpadding="10">
			<c:forEach items="${bookPage.list }" var="book">
				<tr>
					<td><a href="bookServlet?method=getBook&pageNo=${bookPage.pageNo }&bookId=${book.bookId}">${book.title }</a><br> 
					${book.author }<br>
					</td>
					<td>${book.price }</td>
					<td><a href="bookServlet?method=addToCart&pageNo=${bookPage.pageNo }&bookId=${book.bookId}&title=${book.title}">加入购物车</a></td>
				</tr>
			</c:forEach>
		</table>

		<!-- 注意调用的是request属性bookPage的属性名，也就是get或set方法去掉get后的，并将其首字母改为小写即可 
		如果方法是isHasNext(),则属性名为hasNext
		-->
		<br> <br> 共${bookPage.totalPageNumber }页 &nbsp;&nbsp;
		当前第${bookPage.pageNo }页 &nbsp;&nbsp;
		<c:if test="${bookPage.hasPrev }">
			<a href="bookServlet?method=getBooks&pageNo=1">首页</a>
			&nbsp;&nbsp;
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.prevPage }">上一页</a>
		</c:if>
		&nbsp;&nbsp;
		<c:if test="${bookPage.hasNext }">
			<a href="bookServlet?method=getBooks&pageNo=${bookPage.nextPage }">下一页</a>
			&nbsp;&nbsp;
			<a
				href="bookServlet?method=getBooks&pageNo=${bookPage.totalPageNumber }">尾页</a>
		</c:if>
		&nbsp;&nbsp; 转到<input type="text" size="1" id="pageNo" />页
	</center>
</body>
</html>