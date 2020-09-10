package com.lms.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ChangePassServlet extends HttpServlet {

    private String newPass;
    private int uid;
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
    {
        uid=Integer.parseInt((request.getSession(false).getAttribute("uid")).toString());
        String oldPass=request.getParameter("oldPassword");
        newPass=request.getParameter("newPassword");
        try {
            Connection conn=getConnection();
            changePassword(conn);
            request.setAttribute("message","password changed");
            request.getRequestDispatcher(new String(request.getRequestURL())).forward(request,response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn= DriverManager.getConnection("jdbc:mysql://192.168.5.1543306/leave_management", "root","cxps123");
        return conn;
    }

    public void changePassword(Connection conn) throws SQLException {
        Statement stat=conn.createStatement();
        stat.executeUpdate("UPDATE users SET password="+newPass+" WHERE userId="+uid+"");
    }
}
