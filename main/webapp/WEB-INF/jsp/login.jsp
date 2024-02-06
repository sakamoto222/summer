<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%
//リクエストスコープからのデータの取得
String errorMsg = (String)request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メモリンログイン</title>
  <link rel="stylesheet" href="./css/login.css">
</head>
<body>
	<h1>ログイン</h1>
	<div class="contact">
	<form  action="/memoapp/LoginServlet" method="post">
		<div class="inputarea">
			<P>ユーザー名：<input class="input" type="text" name="username" ></P>
			<P>パスワード：<input class="input"type="password" name="password"></P>
			<%if(errorMsg != null){%>	
				<p><%= errorMsg %></p>
			<%}else{ %>
			<%} %>
		</div>
		
		<div class="buttonarea">
			<div class="button">
				<input  type="submit" value="ログインする">
			</div>
			<div class="button">
				<input  type="button" value="初めてご利用の方はこちら" onclick="location.href='./UserRegister'">
			</div>
		</div>
	</form>
	</div>
</body>
</html>