<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Memo,java.util.List"%>
<%
//リクエストスコープからのデータの取得
String username = (String)session.getAttribute("username");
List<Memo> memoList = (List<Memo>)session.getAttribute("memoList"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メモリン</title>
	<link rel="stylesheet" href="./css/memo.css">
</head>
<body>
	<div class="header">
		<h1>メモリスト</h1>
		<p>ようこそ<%= username %>さん</p>
	</div>
    
   <table>
	    <caption>メモリスト</caption>
	    <!-- テーブルのヘッダー部分 -->
	    <thead>
	        <tr>
	            <th class="id" scope="col">ID</th> <!-- ID表示用のヘッダーを追加 -->
	            <th class="date" scope="col">日付</th>
	            <th class="content" scope="col">メモ内容</th>
	        </tr>
	    </thead>
	    <!-- テーブルの内容部分 -->
	    <tbody>
	        <% for(Memo memo : memoList) { %>
	        <tr>
	            <td class="id"><%= memo.getMemoId() %></td> <!-- IDを表示しないが、隠しフィールドとして保持 -->
	            <td><%= memo.getDate() %></td>
	            <td><%= memo.getContent() %></td>
	        </tr>
	        <% } %>
	    </tbody>
	</table>

    <p><a href="/memoapp/MemoServlet">メモを編集</a></p>
    
</body>
</html>