<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>

<body>
<!-- 상대경로 사용, [현재 URL이 속한 계층 경로 + /save] -->
<%-- 상대 경로는 /를 붙이지 않는다. 그럼 현재 경로의 맨 끝을 대체한다.--%>
<form action="save" method="post">
    username: <input type="text" name="username"/>
    age: <input type="text" name="age"/>
    <button type="submit">전송</button>
</form>
</body>
</html>
