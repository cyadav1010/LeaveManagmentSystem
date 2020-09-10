package com.lms.servlets;

import com.lms.models.LeaveLogs;
import com.lms.models.User;

import javax.jws.WebService;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginServlet extends HttpServlet{
    int userId;
    String passInDB;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        passInDB=null;
        Connection conn=null;
        try {
            conn=getConnection();
            User user=null;
            List<LeaveLogs> logsList=null;
           List<LeaveLogs> appliedList=null;
            List<User> subordinates=null;
            if(authenticate(conn,email,password)) {
                user = getDetails(conn, userId);
                System.out.println("User:"+user.getName());
                HttpSession session=request.getSession();
                session.setAttribute("sessionUserId",user.getuserId());
                session.setAttribute("sessionIsManager",user.getIsManager());
                request.setAttribute("user", user);
                new HomeServlet().doPost(request,response);
            }else {
                response.setContentType("text/html");
                System.out.println("No user");
                PrintWriter out=response.getWriter();
                out.print("<script type=\"text/javascript\">\n" +
                        "    alert(\"Wrong ID and Paasword\");\n" +
                        "</script>");
                request.getRequestDispatcher("Home.html").include(request, response);
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean authenticate(Connection conn,String email,String password) throws SQLException {

        PreparedStatement ps = conn.prepareStatement("SELECT user_id,password FROM users where email=?");
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        if(rs.first()) {
            passInDB = rs.getString(2);
            userId = rs.getInt(1);
        }else System.out.println("no result from sql");

        if(password.equals(passInDB)){
            return true;
        }
        return false;
    }

    private int getUserNotificationsCount(Connection conn) throws SQLException{
        int count=0;
        List<LeaveLogs> userNotificationList=new ArrayList<LeaveLogs>();
        PreparedStatement ps = conn.prepareStatement("Select count(*) from leave_logs where user_id=? and seen_status=0 and leave_status !=0");
        ps.setInt(1,userId);
        ResultSet rs=ps.executeQuery();
        rs.first();
        count=rs.getInt(1);
        return count;
    }

    private List<LeaveLogs> getUserNotificationList(Connection conn) throws SQLException {
        List<LeaveLogs> userNotificationList=new ArrayList<LeaveLogs>();
        PreparedStatement ps = conn.prepareStatement("Select user_id,leave_status,from_date,to_date,reason from leave_logs where user_id=? and seen_status=0 and leave_status !=0");
        ps.setInt(1,userId);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            LeaveLogs leave =new LeaveLogs();
            leave.setUserId(rs.getInt(1));
            leave.setLeaveStatus(rs.getInt(2));
            leave.setFromDate(rs.getString(3));
            leave.setToDate(rs.getString(4));
            leave.setReason(rs.getString(5));
            userNotificationList.add(leave);
        }
        return userNotificationList;

    }

    private int getManagerNotificationsCount(  Connection conn) throws SQLException {
            int count = 0;
            PreparedStatement ps=conn.prepareStatement("select user_id,no_of_days,from_date,to_date,reason from leave_logs where user_id in (select user_id from users where manager_id = ?) and leave_status =0 UNION select user_id,no_of_days,from_date,to_date,reason from leave_logs where leave_status=0 and user_id != ? and user_id in(select user_id from users where is_manager=1)");
            ps.setInt(1, userId);
            ps.setInt(2,userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               count++;
            }
            return count;
    }

    private User getDetails(Connection conn,int userId) throws SQLException {
        PreparedStatement statement=conn.prepareStatement("SELECT * FROM users WHERE user_id=?");
        statement.setInt(1,userId);
        ResultSet rs=statement.executeQuery();
        User user=null;
        if(rs.first()){
            user = new User();
            user.setuserId(rs.getInt(1));
            user.setName(rs.getString(2));
            user.setEmail(rs.getString(3));
            user.setDepartment(rs.getString(4));
            user.setRole(rs.getString(5));
            user.setIsManager(rs.getBoolean(7));
            user.setManagerId(rs.getInt(8));
            user.setJoinDate(rs.getString(9));
            user.setTotalLeaves(rs.getInt(10));
            user.setLeavesTaken(rs.getInt(11));
        }

        return user;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn=null;
        int notificationCount;
        try {

            conn=getConnection();
            PreparedStatement ps= conn.prepareStatement("Select is_manager from users where user_id=?");
            ps.setInt(1,userId);
            while(userId==0);
            ResultSet rs=ps.executeQuery();
           rs.first();
            if(rs.getInt(1)!=0)
                notificationCount=getManagerNotificationsCount(conn);
            else {
                notificationCount = getUserNotificationsCount(conn);

            }
            String x= Integer.toString(notificationCount);
            response.getWriter().write(x);
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }

