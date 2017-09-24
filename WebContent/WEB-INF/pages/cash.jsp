<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<br>
		<br> 您一共买了 ${sessionScope.ShoppingCart.bookNumber } 本书 <br>
		<br> 应付 ￥${sessionScope.ShoppingCart.totalMoney } <br>
		<br>
		
		<c:if test="${requestScope.errors != null}">
			<font color="red">${requestScope.errors}</font>
		</c:if>

		<form action="bookServlet?method=cash" method="post">
			<table cellpadding="10"> 
				<tr>
					<td>信用卡姓名:</td>
					<td><input type="text" name="userName"/></td>
				</tr>
				<tr>
					<td>信用卡账号:</td>
					<td><input type="text" name="accountId"/></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="付款" size="2"/>
					</td>
				</tr>
			
			</table>
		</form>
	</center>

</body>
</html>