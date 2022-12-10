<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>用户登录</h1>
    <hr/>
    <form action='<%=request.getContextPath()%>/user/login' method='post'>
        <label>用户名:<input type='text' name='username' /></label><br>
        <label>密码:<input type='password' name='password' /></label><br>
        <input type='submit' value='登录' /><br>
    </form>
</body>
</html>