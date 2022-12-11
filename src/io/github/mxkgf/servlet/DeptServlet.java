package io.github.mxkgf.servlet;

import io.github.mxkgf.bean.Dept;
import io.github.mxkgf.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/dept/list", "/dept/save", "/dept/detail", "/dept/delete", "/dept/modify"})
public class DeptServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        // 设置响应字内容类型和字符集，防止中文乱码
        response.setContentType("text/html;charset=UTF-8");
        // 获取servlet path
        String servletPath = request.getServletPath();
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            if ("/dept/list".equals(servletPath)) {
                doList(request, response);
            } else if ("/dept/save".equals(servletPath)) {
                doSave(request, response);
            } else if ("/dept/detail".equals(servletPath)) {
                doDetail(request, response);
            } else if ("/dept/delete".equals(servletPath)) {
                doDel(request, response);
            } else if ("/dept/modify".equals(servletPath)) {
                doModify(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }

    private void doModify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        String contextPath = request.getContextPath();
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");

        // 连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        int count;
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 获取预编译数据库对象
            String sql = "update dept  set dname = ?, loc = ? where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dname);
            ps.setString(2, loc);
            ps.setInt(3, deptno);
            // 执行sql
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭连接
            DBUtil.close(conn, ps, null);
        }
        if (count == 1) {
            response.sendRedirect(contextPath + "/dept/list");
            // request.getRequestDispatcher("/dept/list").forward(request, response);
        }
    }

    private void doDel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getContextPath();
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        // 连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        int count;
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            // 获取预编译数据库对象
            String sql = "delete from oa.dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, deptno);
            // 执行sql
            count = ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        } finally {
            // 关闭连接
            DBUtil.close(conn, ps, null);
        }
        if (count == 1) {
            response.sendRedirect(contextPath + "/dept/list");
            // request.getRequestDispatcher("/dept/list").forward(request, response);
        }
    }

    private void doDetail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int deptno = Integer.parseInt(request.getParameter("deptno"));

        // 连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Dept dept = new Dept();

        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 获取预编译数据库对象
            String sql = "select deptno, dname, loc from dept where deptno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, deptno);
            // 执行sql
            rs = ps.executeQuery();
            // 处理结果集
            if (rs.next()) {
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭连接
            DBUtil.close(conn, ps, rs);
        }
        request.setAttribute("dept", dept);
        request.getRequestDispatcher("/" + request.getParameter("f") + ".jsp").forward(request, response);
    }

    private void doSave(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int deptno = Integer.parseInt(request.getParameter("deptno"));
        String dname = request.getParameter("dname");
        String loc = request.getParameter("loc");

        Connection conn = null;
        PreparedStatement ps = null;
        int count;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into dept values(?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, deptno);
            ps.setString(2, dname);
            ps.setString(3, loc);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
        if (count == 1) {
            response.sendRedirect(request.getContextPath() + "/dept/list");
            // request.getRequestDispatcher("/dept/list").forward(request, response);
        }
    }

    private void doList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        String contextPath = request.getContextPath();
        List<Dept> deptList = new ArrayList<>();
        // 连接数据库
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 获取预编译数据库对象
            String sql = "select * from dept";
            ps = conn.prepareStatement(sql);
            // 执行sql
            rs = ps.executeQuery();
            // 处理结果集
            int i = 0;
            while (rs.next()) {
                int deptno = rs.getInt("deptno");
                String dname = rs.getString("dname");
                String loc = rs.getString("loc");
                Dept dept = new Dept();
                dept.setDeptno(deptno);
                dept.setDname(dname);
                dept.setLoc(loc);
                deptList.add(dept);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭连接
            DBUtil.close(conn, ps, rs);
        }
        request.setAttribute("deptList", deptList);
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }
}
