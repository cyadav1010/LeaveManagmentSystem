package com.lms.servlets;

import com.lms.models.LeaveLogs;
import com.lms.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeServlet")
public class HomeServlet extends HttpServlet {
    int userId;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn= null;
        HttpSession session = request.getSession(false);
        if(session.getAttribute("sessionUserId")!=null) {
        userId=Integer.parseInt(session.getAttribute("sessionUserId").toString());
        try {
            conn = getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        User user=null;
        List<LeaveLogs> logsList=null;
        List<LeaveLogs> appliedList=null;
        List<User> subordinates=null;
        try {
            user = getDetails(conn, userId);
            logsList = getLogs(conn, userId);
            System.out.println("summery in logsList");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<String> statusName = new ArrayList<String>();
        statusName.add("waiting");
        statusName.add("Approved");
        statusName.add("Rejected");
        request.setAttribute("user", user);
            if (!user.getIsManager()) {
                String managerName = null;
                try {
                    managerName=getManagerName(conn,userId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                request.setAttribute("manager", managerName);
                System.out.println("manager fo ruser="+managerName);
                request.setAttribute("leaveLogs", logsList);
                request.setAttribute("StatusName", statusName);
                RequestDispatcher rd = request.getRequestDispatcher("userProfile.jsp");
                rd.forward(request, response);
            } else {
                try {
                    subordinates = getSubordinates(conn, userId);
                    request.setAttribute("subordinates", subordinates);
                    request.setAttribute("appliedLeaves", getLeavesApplied(conn, userId));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                request.setAttribute("leaveLogs", logsList);
                request.setAttribute("StatusName", statusName);
                RequestDispatcher rd = request.getRequestDispatcher("Manager.jsp");
                rd.forward(request, response);

            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            response.sendRedirect("Home.html");
        }
    }
    private List<LeaveLogs> getLeavesApplied(Connection conn, int userId) throws SQLException {
        PreparedStatement ps=conn.prepareStatement("select user_id,no_of_days,from_date,to_date,reason from leave_logs where user_id in (select user_id from users where manager_id = ?) and leave_status =0 UNION select user_id,no_of_days,from_date,to_date,reason from leave_logs where leave_status=0 and user_id != ? and user_id in(select user_id from users where is_manager=1)");
         ps.setInt(1,userId);
        ps.setInt(2,userId);
        ResultSet rs=ps.executeQuery();
        List<LeaveLogs> logs=new ArrayList<LeaveLogs>();
        while(rs.next()){
            LeaveLogs log=new LeaveLogs();
            log.setUserId(rs.getInt(1));
            log.setUserName(getUserName(conn,rs.getInt(1)));
           // System.out.println("test homeServlet-name in Leaves "+log.getUserName());
            log.setDays(rs.getInt(2));
            log.setFromDate(String.valueOf(rs.getDate(3)));
            log.setToDate(String.valueOf(rs.getDate(4)));
            log.setReason(rs.getString(5));
            logs.add(log);
        }
        return logs;
    }

    private String getUserName(Connection conn, int userId) throws SQLException {
        PreparedStatement ps=conn.prepareStatement("select user_name from users where user_id=?");
        ps.setInt(1,userId);
        ResultSet rs=ps.executeQuery();
        rs.first();
       // System.out.println("User Name ="+rs.getString(1));
        return rs.getString(1);
    }
    private String getManagerName(Connection conn,int userid) throws SQLException {
        PreparedStatement ps=conn.prepareStatement("select user_name from users where user_id in(select manager_id from users where user_id=?)");
        ps.setInt(1,userid);
        ResultSet rs=ps.executeQuery();
        rs.first();
        return rs.getString(1);

    }

    private List<User> getSubordinates(Connection conn, int userId) throws SQLException {
        List<User> subordinates=new ArrayList<User>();
        PreparedStatement ps=conn.prepareStatement("SELECT * from users where manager_id=?");
        ps.setInt(1,userId);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            User user=new User();
            user.setuserId(rs.getInt(1));
            user.setName(rs.getString(2));
            user.setLeavesTaken(rs.getInt(11));
            user.setDepartment(rs.getString(4));
            user.setRole(rs.getString(5));
            user.setJoinDate(rs.getString(9));
            user.setTotalLeaves(rs.getInt(10));
            subordinates.add(user);
        }
        return subordinates;
    }

    private List<LeaveLogs> getLogs(Connection conn, int userId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM leave_logs where user_id=? ");

        ps.setInt(1,userId);
        ResultSet rs = ps.executeQuery();
        List<LeaveLogs> leaveLogsList= new ArrayList<LeaveLogs>();
        while(rs.next()){
            LeaveLogs logs=new LeaveLogs();
            logs.setUserId(rs.getInt(1));
            logs.setUserName(getUserName(conn,logs.getUserId()));
            logs.setLeaveStatus(rs.getInt(2));
            logs.setSeenStatus(rs.getInt(3));
            logs.setApprovedBy(getUserName(conn,rs.getInt(4)));
            logs.setApprovedOn(rs.getString(5));
            logs.setDays(rs.getInt(6));
            logs.setFromDate(rs.getString(7));
            logs.setToDate(rs.getString(8));
            logs.setReason(rs.getString(9));
          //  System.out.println("test summary:"+logs.getUserName());
            leaveLogsList.add(logs);
        }
        return leaveLogsList;
    }
//    private int getNotificationsCount(  Connection conn) throws SQLException {
//        try {
//            int count = 0;
//            PreparedStatement ps = conn.prepareStatement("Select count(*) from leave_logs where user_id in (SELECT user_id from users where manager_id = ?)  and leave_status=?");
//            ps.setInt(1, userId);
//            ps.setInt(2, 0);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                return rs.getInt(1);
//            }
//        }catch (Exception e){
//
//        }
//        return -1;
//    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
}
