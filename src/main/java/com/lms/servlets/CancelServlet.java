package com.lms.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CancelServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginServlet ls = new LoginServlet();
        try {
            Connection conn=ls.getConnection();
            String deleteParticularRow="delete from leavesWaiting where userId=? fromDate=? toDate=?";
            PreparedStatement ps=conn.prepareStatement(deleteParticularRow);
            int userId=Integer.parseInt(request.getParameter("userId"));
            ps.setInt(1,userId);
            ps.setString(2,request.getParameter("fromDate"));
            ps.setString(3,request.getParameter("toDate"));
            ps.execute();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
