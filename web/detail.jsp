<%@ page import="io.github.mxkgf.bean.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>Document</title>
</head>

<body>
    <%
        Dept dept = (Dept) request.getAttribute("dept");
    %>
    <h3>欢迎<%=session.getAttribute("username")%></h3>
    <a href="<%=request.getContextPath()%>/user/exit">exit</a>
    部门编号：<%=dept.getDeptno()%><br>
    部门名称：<%=dept.getDname()%><br>
    部门位置：<%=dept.getLoc()%><br>
    <input type='button' value='后退' onclick='window.history.go(-1)'>
</body>
</html>