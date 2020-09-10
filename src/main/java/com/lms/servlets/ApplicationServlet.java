package com.lms.servlets;

import com.lms.service.LeaveManagement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "ApplicationServlet")
public class ApplicationServlet extends HttpServlet {
    int userId;
    private String fromDate;
    private String toDate;
    private String reason;
    private int noOFDays;

    //String manager;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn=null;
        HttpSession session=request.getSession(false);
        userId= Integer.parseInt(session.getAttribute("sessionUserId").toString());
        fromDate= request.getParameter("from");
        toDate = request.getParameter("to");
        reason = request.getParameter("reason");
        LeaveManagement leaveManagement = new LeaveManagement();
        try {
            noOFDays=leaveManagement.totalLeave(fromDate,toDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            conn=getConnection();
            insertIntoLeaveLogs(conn,userId);
           conn.close();
           new HomeServlet().doPost(request,response);
//            RequestDispatcher rd = request.getRequestDispatcher("HomeServlet");
//            rd.forward(request,response);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void insertIntoLeaveLogs(Connection conn, int userId) throws SQLException {
        PreparedStatement statement=conn.prepareStatement("insert into leave_logs(user_id,leave_status,seen_status,no_of_days,from_date,to_date,reason) VALUES (?,?,?,?,?,?,?)");
        statement.setInt(1,userId);
        statement.setInt(2,0);
        statement.setInt(3,0);
        statement.setInt(4,noOFDays);
        statement.setString(5,fromDate);
        statement.setString(6,toDate);
        statement.setString(7,reason);
        statement.execute();
    }

    public Connection getConnection() throws ClassNotFoundException {
        Connection conn=null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            conn= DriverManager.getConnection(
                    "jdbc:mysql://192.168.5.154:3306/leave_management", "root","cxps123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
