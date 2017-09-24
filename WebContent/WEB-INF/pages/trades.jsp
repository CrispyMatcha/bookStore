<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>

		<br> <br>
		<h4>UserName:${user.userName }</h4>
		<br>
		<table>
			<c:forEach items="${user.trades }" var="trade">
				<tr>
					<td>
						<table border="1" cellpadding="10px" cellspacing="0">
							<tr>
								<td colspan="3">TradeTime:${trade.tradeTime }</td>
							</tr>
							<tr>
								<td>书名</td>
								<td>单价</td>
								<td>数量</td>
							</tr>
							<c:forEach items="${trade.items }" var="tradeItem">

								<tr>
									<td>${tradeItem.book.title }</td>
									<td>${tradeItem.book.price }</td>
									<td>${tradeItem.quantity }</td>
								</tr>
								<br>
							</c:forEach>
							<br><br>
						</table>
					</td>
				</tr>
			</c:forEach>
		</table>

	</center>
</body>
</html>