<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザ登録画面</title>
</head>
<body>
	<div class="userform">
		<form action="/memoapp/UserRegister" method="post">
			<div class="inputarea">
				<P>ユーザー名：<input class="input" type="text" name="username" ></P>
				<P>パスワード：<input class="input" type="password" name="password"></P>
			</div>
			
			<div class="buttonarea">
				<div class="button">
					<input  type="submit" value="登録する">
				</div>
				<div class="button">
					<input  type="reset" value="キャンセル">
				</div>
			</div>
		</form>
	</div>
</body>
</html>