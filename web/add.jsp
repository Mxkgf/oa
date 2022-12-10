<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang='en'>
  <head>
    <meta charset='UTF-8' />
    <title>增加部门</title>
  </head>
  <body>
    <h1>增加部门</h1>
    <hr/>
    <form action='<%=request.getContextPath()%>/dept/save' method='post'>
      <label>部门编号:<input type='text' name='deptno' /></label><br>
      <label>部门名称<input type='text' name='dname' /></label><br>
      <label>部门位置<input type='text' name='loc' /></label><br>
      <input type='submit' value='保存' /><br>
    </form>
  </body>
</html>
