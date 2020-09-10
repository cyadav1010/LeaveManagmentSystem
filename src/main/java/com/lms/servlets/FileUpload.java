package com.lms.servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileUpload extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        InputStream inputStream=null;
        List<String>  csvStrings= new ArrayList();
        DiskFileItemFactory factory = new DiskFileItemFactory();

        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        ServletFileUpload upload = new ServletFileUpload(factory);

        List<FileItem> items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        if (null != items) {
            for (FileItem item : items) {
                inputStream = item.getInputStream();
                if (null != inputStream) {
                    BufferedReader br = new BufferedReader( new InputStreamReader(inputStream));
                    String line;
                    int flag=0;
                    while((line=br.readLine())!=null){
                        if(flag==1)
                            csvStrings.add(line);
                        else
                            flag=1;
                    }
                    br.close();
                    splitListToArray(csvStrings);
                }
            }
        }
        request.getRequestDispatcher("/Manager.jsp").forward(request,response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    protected void splitListToArray(List<String> csvStrings){
        String[] dataArray=null;
        Iterator<String> itr= csvStrings.listIterator();
        while(itr.hasNext()){
            String line= itr.next();
            dataArray=line.split(",");
            addToDatabase(dataArray);
        }

    }
    protected void addToDatabase(String[] dataArray){
        try {
            LoginServlet ls=new LoginServlet();
            Connection conn = ls.getConnection();
            PreparedStatement stmt=conn.prepareStatement("INSERT INTO users(firstName,email,department,role,password,isManager) VALUES (?,?,?,?,?,?)");
            stmt.setString(1,dataArray[0]);
            stmt.setString(2,dataArray[1]);
            stmt.setString(3,dataArray[2]);
            stmt.setString(4,dataArray[3]);
            stmt.setString(5,dataArray[4]);
            int isManagerValue =Integer.parseInt(dataArray[5]);
            stmt.setInt(6,isManagerValue);
            stmt.execute();
            stmt.close();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

