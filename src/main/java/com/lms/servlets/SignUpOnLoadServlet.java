package com.lms.servlets;

import com.lms.models.Departments;
import com.lms.models.Manager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SignUpOnLoadServlet")
public class SignUpOnLoadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection conn=getConnection();
            System.out.println("Started signup on load servlet");
            PreparedStatement ps= conn.prepareStatement("select user_id,user_name,email from users where is_manager=1");
            ResultSet rs=ps.executeQuery();
            List<Manager> managerIdList=new ArrayList<Manager>();
            PreparedStatement psd = conn.prepareStatement("select * from departments");
            ResultSet rsd =psd.executeQuery();
            List<Departments> departmentsList =new ArrayList<Departments>();
            while(rsd.next()){
                Departments departments = new Departments();
                departments.setDepID(rsd.getInt(1));
                departments.setDepName(rsd.getString(2));
                departmentsList.add(departments);
            }
            while(rs.next()){
                Manager manager= new Manager();
                manager.setManagerId(rs.getInt(1));
                manager.setManagerName(rs.getString(2));
                manager.setEmail(rs.getString(3));
                managerIdList.add(manager);
                System.out.println("Got ManagerList"+managerIdList.get(0).getEmail());
            }
            conn.close();
            request.setAttribute("managersList",managerIdList);
            request.setAttribute("departmentsList",departmentsList);
            request.getRequestDispatcher("Signup.jsp").forward(request,response);
            //response.sendRedirect("Signup.jsp");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
