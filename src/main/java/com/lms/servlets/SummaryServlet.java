package com.lms.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SummaryServlet extends HttpServlet {

    private Date date;
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        try {
            date=new SimpleDateFormat("MM/DD/YYYY").parse(request.getParameter("date"));
            if(!checkWeekend(date)) {
                Connection conn = getConnection();
              //  getSummary(conn);
              //  request.setAttribute("leaveList",leavesList);
            }
            else {
            }
            request.getRequestDispatcher("Manager.jsp").forward(request,response);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    private void getSummary(Connection conn) throws SQLException {
//        List<Integer> uids=new ArrayList<Integer>();
//        Statement statement=conn.createStatement();
//        ResultSet rs=statement.executeQuery("SELECT applied_by,from_date,to_date FROM leave_log");
//        while(rs.next())
//        {
//            Date startDate=rs.getDate("fromDate");
//            Date endDate=rs.getDate("toDate");
//            if(onLeave(startDate,endDate))
//                uids.add(rs.getInt("appliedBy"));
//        }
//        for(int uid:uids) {
//            ResultSet list = statement.executeQuery("Select applied_by,from_date,toDate,reason FROM leavelog WHERE appliedBy=" +uid);
//            Leaves leaveDetails=new Leaves();
//            leaveDetails.setEndDate(String.valueOf(list.getDate("toDate")));
//            leaveDetails.setStartDate(String.valueOf(list.getDate("fromDate")));
//            leaveDetails.setName(list.getString("appliedBy"));
//            leaveDetails.setReason(list.getString("reason"));
//            leavesList.add(leaveDetails);
//        }
//    }

    private boolean onLeave(Date startDate, Date endDate) {

        return startDate.compareTo(date) * date.compareTo(endDate) >= 0;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn= DriverManager.getConnection("jdbc:mysql://192.168.5.1543306/leave_management", "root","cxps123");
        return conn;
    }

    public boolean checkWeekend(Date date)
    {
        String dateString=date.toString();
        int mm=Integer.parseInt(dateString.split("/")[0]);
        int dd=Integer.parseInt(dateString.split("/")[1]);
        int yyyy=Integer.parseInt(dateString.split("/")[2]);
        Calendar startDate = Calendar.getInstance();
        startDate.set(yyyy, mm, dd);
        if ((startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)||(startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) {
            return true;
        }
        return false;
    }
}
