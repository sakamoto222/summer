<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>追加画面</title>
</head>
<body>
    <h1>新しいメモ</h1>
    <form action="MemoServlet" method="post">
        <label>Date:</label><br>
        <input type="text" name="date"><br>
        <label>Content:</label><br>
        <textarea name="content"></textarea><br>
        <input type="submit" value="Add Memo">
        <input type="hidden" name="action" value="add">
    </form>
    <p><a href="MemoServlet">View Memos</a></p>
</body>
</html>