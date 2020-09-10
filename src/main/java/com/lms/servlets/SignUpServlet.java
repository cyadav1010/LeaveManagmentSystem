package com.lms.servlets;
import com.lms.service.LeaveManagement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@WebServlet(name="SignUp")
public class SignUpServlet extends HttpServlet {
    String name;
    String lastName;
    String email;
    String department;
    String role;
    String password;
    String cpassword;
    boolean isManager;
    String managerId;
    java.util.Date joiningDate;
    String dateOfJoining;
    int totalLeaves;
    int leavesTaken;
    @Override
    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        name=request.getParameter("name");
        email=request.getParameter("emailid");
        dateOfJoining=request.getParameter("joindate");
        department=request.getParameter("department");
        role=request.getParameter("role");
        if(role.equals("manager")){
            isManager=true;
            managerId=null;
        }else{

            isManager=false;
            managerId=request.getParameter("managerId");
        }
        LeaveManagement leaveManagement =new LeaveManagement();
        leaveManagement.setDay(dateOfJoining);
        leavesTaken=0;
        totalLeaves = leaveManagement.leavesPerQuater();
        password=request.getParameter("password");
        cpassword=request.getParameter("confirm");
      //  try {
           // joiningDate =  new SimpleDateFormat("YYYY-DD-MM").parse(dateOfJoining);
       // } catch (ParseException e) {
        //    e.printStackTrace();
       // }
        try {
            Connection conn= getConnection();
            insertData(conn);
            conn.close();
            response.sendRedirect("Home.html");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public Connection getConnection() throws ClassNotFoundException {
        Connection conn=null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            conn= DriverManager.getConnection(
                    "jdbc:mysql://192.168.5.154:3306/leave_management", "root","cxps123");
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public void insertData(Connection conn) throws SQLException {

        PreparedStatement statement=conn.prepareStatement("INSERT INTO users(user_name,email,department,role,password,is_manager,manager_id,joining_date,total_leaves,leave_taken) VALUES(?,?,?,?,?,?,?,?,?,?) ");
        statement.setString(1,name);
        statement.setString(2,email);
        statement.setString(3,department);
        statement.setString(4,role);
        statement.setString(5,password);
        statement.setBoolean(6,isManager);
        statement.setString(7,managerId);
        statement.setString(8,dateOfJoining);
        statement.setInt(9,totalLeaves);
        statement.setInt(10,leavesTaken);
        statement.execute();

    }
}






