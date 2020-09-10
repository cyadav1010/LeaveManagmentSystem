package com.lms.servlets;

import com.lms.models.User;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(name = "approveRejectServlet")
public class ApproveRejectServlet extends HttpServlet {
    private int userId;
    private String name;
    private String fromDate;
    private String toDate;
    private int no_of_days;
    private int managerId;
    Connection conn;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession(false);

        System.out.println("Seesion Id:"+session.getAttribute("sessionUserId"));
        managerId=Integer.parseInt(session.getAttribute("sessionUserId").toString());
        userId = Integer.parseInt(request.getParameter("user-id"));
        fromDate=request.getParameter("from");
        toDate=request.getParameter("to");
        no_of_days=Integer.parseInt(request.getParameter("no_of_days"));
        if(request.getParameter("approve")!=null){

            System.out.println("Approve"+userId);
            conn=null;
            try {
                conn=getConnection();
                approveLeave(conn);
                conn.close();
                new HomeServlet().doPost(request,response);
//                RequestDispatcher rd = request.getRequestDispatcher("HomeServlet.java");
//                rd.forward(request,response);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }else{

            System.out.println("reject"+userId);

            try {
                conn = getConnection();
                rejectLeave(conn);
                conn.close();
                new HomeServlet().doPost(request,response);
//
//                RequestDispatcher rd = request.getRequestDispatcher("login");
//                rd.forward(request,response);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    Connection getConnection() throws ClassNotFoundException, SQLException {
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


    void approveLeave(Connection conn) throws SQLException, ParseException {
        PreparedStatement ps=conn.prepareStatement("UPDATE leave_logs set leave_status =1 , approved_by = ? where user_id =? and from_date =? and to_date =?");
        ps.setInt(1,managerId);
        ps.setInt(2,userId);
        ps.setString(3,fromDate);
        ps.setString(4,toDate);
        ps.execute();
        ps=conn.prepareStatement("update users set leave_taken =leave_taken+? where user_id=?");
        ps.setInt(1,no_of_days);
        ps.setInt(2,userId);
        ps.execute();

    }
    void rejectLeave(Connection conn) throws SQLException, ParseException {
        PreparedStatement ps=conn.prepareStatement("UPDATE leave_logs set leave_status =2 , approved_by = ? where user_id =? and from_date =? and to_date =?");
        ps.setInt(1,managerId);
        ps.setInt(2,userId);
        ps.setString(3,fromDate);
        ps.setString(4,toDate);
        ps.execute();
    }

}
