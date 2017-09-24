<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="script/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	$(function() {
		$(".delete").click(function() {
			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());
			var flag = confirm("确定要删除 " + title + " 的信息吗？");

			if (flag) {
				return true;
			}
			return false;
		});

		//ajax 修改单个商品的数量
		//1.获取页面中所有的text，(只是quantity是text)，并为其添加onchange 响应函数。并弹出确认对话框，确定要修改吗？
		$(":text").change(function() {
			var quantityVal = $.trim(this.value);
			var flag = false;
			
			var reg = /^\d+$/g;
			var quantity = -1;
			if (reg.test(quantityVal)) {
				quantity = parseInt(quantityVal);
				if (quantity >= 0) {
					flag = true;
				}
			}
			
			if(!flag){
				alert("您的输入不合法,请重新输入");
				//恢复之前合法的数据
				$(this).val($(this).attr("class"));
				return;
			}
			
			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());
			if(quantity == 0){
				var flag2 = confirm("确定要删除" + title + "吗？");
				if(flag2){
					//得到了a节点
					var $a = $tr.find("td:last").find("a");
					
					//执行a节点的onclick响应函数
					$a[0].onclick();
					//var serializeVal = $(":hidden").serialize();
					//href = href + "&" + serializeVal;
					//window.location.href = href;
					return;
				}
			}

			var flag = confirm("确定要修改" + title + "的数量吗？");

			if (!flag) {
				$(this).val($(this).attr("class"));
				return;
			}
			//2.请求地址为：bookServlet
			var url = "bookServlet";
			//3.请求参数为：method:updateItemQuantity,bookId:name属性值，quantity:val,time:new Date()
			var bookIdVal = $.trim(this.name);
			var args = {
				"method" : "updateItemQuantity",
				"bookId" : bookIdVal,
				"quantity" : quantityVal,
				"time" : new Date()
			};

			//4.在updateItemQuantity 方法中，获取quantity,bookId ,再获取购物车对象，调用service方法做修改

			//5.传回json 数据：bookNumber :xx ,totalMoney
			//6.更新当前页面的bookNumber 和 totalMoney
			$.post(url, args, function(data) {
				var bookNumber = data.bookNumber;
				var totalMoney = data.totalMoney;

				$("#totalMoney").text("总金额：￥" + totalMoney);
				$("#bookNumber").text("您的购物车中共有" + bookNumber + "本书");
			}, "JSON");
		});

	})
</script>
<%@ include file="/commons/queryCondition.jsp"%>
</head>
<body>
	<center>
		<br> <br>
		<div id="bookNumber" style="color: #0000FF">
			<h3>您的购物车中共有 ${sessionScope.ShoppingCart.bookNumber }本书</h3>
		</div>
		<br> <br>
		<table cellpadding="10">
			<tr>
				<td>Title</td>
				<td>Quantity</td>
				<td>Price</td>
				<td>&nbsp;</td>
			</tr>

			<c:forEach items="${sessionScope.ShoppingCart.items}" var="item">
				<tr>
					<td>${item.book.title }</td>
					<td>
						<!-- name就是当前这本书的bookId,在aiax实现数量的修改时，可以进行id传值 --> 
						<!-- class属性保留原本合法的数据，也就是当输入不合法的时候，还恢复之前合法的数据 -->
						<input
						class="${item.quantity }" type="text" size="1"
						name="${item.book.bookId }" value="${item.quantity }" />
					</td>
					<td>${item.book.price }</td>
					<td><a
						href="bookServlet?method=remove&bookId=${item.book.bookId }&pageNo=${param.pageNo}"
						class="delete">删除</a></td>
				</tr>
			</c:forEach>

			<tr>
				<td colspan="4" id="totalMoney">总金额：￥${sessionScope.ShoppingCart.totalMoney }</td>
			</tr>

			<tr>
				<td colspan="4">
					<a href="bookServlet?method=getBooks&pageNo=${param.pageNo }">继续购物</a>&nbsp;&nbsp;
					<a href="bookServlet?method=clear">清空购物车</a>&nbsp;&nbsp; 
					<a href="bookServlet?method=forwardPage&page=cash">结账</a>
				</td>
			</tr>

		</table>
	</center>

</body>
</html>