<%@ page import="java.util.List" %>
<%@ page import="io.github.mxkgf.bean.Dept" %><%--
  Created by IntelliJ IDEA.
  User: maxiaokang
  Date: 2022/12/8
  Time: 07:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang='en'>
  <head>
      <meta charset='UTF-8' />
      <title>部门列表页面</title>
      <script type='text/javascript'>
              function del(dno) {
                  if (window.confirm('亲，，请确认删除吗？')) {
                      document.location.href = "<%=request.getContextPath()%>/dept/delete?deptno=" + dno
                  }
              }
      </script>
    </head>

  <body>
    <h1 align='center'>部门列表</h1>
    <hr />
    <table border='1'  align='center' width='50%'>
        <tr>
            <th>序号</th>
            <th>部门编号</th>
            <th>部门名称</th>
            <th>操作</th>
        </tr>
        <%
            List<Dept> deptList = (List<Dept>) request.getAttribute("deptList");
            int count = 0;
            for (Dept dept: deptList) {
        %>

        <tr>
            <td><%=++count%></td>
            <td><%=dept.getDeptno()%></td>
            <td><%=dept.getDname()%></td>
            <td>
                <a href='javascript:void(0)' onclick='del(<%=dept.getDeptno()%>)'>删除</a>
                <a href="<%=request.getContextPath()%>/dept/detail?f=edit&deptno=<%=dept.getDeptno()%>">修改</a>
                <a href="<%=request.getContextPath()%>/dept/detail?f=detail&deptno=<%=dept.getDeptno()%>">详情</a>
              </td>
          </tr>
        <%
            }
        %>
      </table>
      <a href="<%=request.getContextPath()%>/add.jsp">增加部门</a>
    </body>
</html>