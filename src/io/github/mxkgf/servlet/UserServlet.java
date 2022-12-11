package io.github.mxkgf.servlet;

import io.github.mxkgf.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/user/login", "/user/exit"})
public class UserServlet extends HttpServlet {
    /**
     * @param request 
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // 设置响应字内容类型和字符集，防止中文乱码
        response.setContentType("text/html;charset=UTF-8");
        // 获取servlet path
        String servletPath = request.getServletPath();
        if ("/user/login".equals(servletPath)) {
            doLogin(request, response);
        } else if ("/user/exit".equals(servletPath)) {
            doExit(request, response);
        }
    }

    private void doExit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath());
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count;
        try {
            conn = DBUtil.getConnection();
            String sql = "select username, password from user where username = ? and password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect(request.getContextPath() + "/dept/list");
            } else {
                response.sendRedirect(request.getContextPath() + "/error.jsp");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }
}
