package com.lms.models;

public class User {

    private int userId;
    private String name;
    private String email;
    private String department;
    private String joinDate;
    private String role;
    private boolean isManager;
    private int managerId;
    private int totalleaves;
    private int leavesTaken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getuserId() {
        return userId;
    }

    public void setuserId(int userId) {
        this.userId= userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joindate) {
        this.joinDate = joindate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getIsManager() {
        return isManager;
    }

    public void setIsManager(boolean isManager)
    {
        this.isManager=isManager;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public void setTotalLeaves(int totalLeaves){
        this.totalleaves=totalLeaves;
    }
    public int getTotalleaves(){
        return totalleaves;
    }
    public void setLeavesTaken(int leavesTaken){
        this.leavesTaken=leavesTaken;
    }
    public int getLeavesTaken(){
        return leavesTaken;
    }

}
