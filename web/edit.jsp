<%@ page import="io.github.mxkgf.bean.Dept" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>修改部门</title>
</head>
<body>
    <h1>修改部门</h1>
    <hr/>
    <%
        Dept dept = (Dept) request.getAttribute("dept");
    %>
    <form action='<%=request.getContextPath()%>/dept/modify' method='post'>
      <label>部门编号:<input type='text' name='deptno' value='<%=dept.getDeptno()%>' readonly/></label><br>
      <label>部门名称:<input type='text' name='dname' value='<%=dept.getDname()%>' /></label><br>
      <label>部门位置:<input type='text' name='loc' value='<%=dept.getLoc()%>' /></label><br>
      <input type='submit' value='修改' /><br>
    </form>
</body>
</html>