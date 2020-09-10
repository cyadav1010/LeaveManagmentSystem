package com.lms.models;

public class Manager {
    int managerId;
    String managerName;
    String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public int getManagerId() {
        return managerId;
    }

    public String getEmail() {
        return email;
    }

    public String getManagerName() {
        return managerName;
    }
}
