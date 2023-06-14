package com.finalyear.project.alumnitracerData;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Student {

    @SerializedName("user")
    private User user;
    private String id;
    @SerializedName("maritialStatus")
    private String maritialStatus;
    @SerializedName("studentStatus")
    private String status;
    @SerializedName("DoB")
    private String dob;
    @SerializedName("course")
    private String course;
    @SerializedName("hall")
    private String hall;

    @SerializedName("regNo")
    private String regNo;





    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaritialStatus() {
        return maritialStatus;
    }

    public void setMaritialStatus(String maritialStatus) {
        this.maritialStatus = maritialStatus;
    }

    public String getStatus() {
        return status;
    }

    public void status(String status) {
        this.status = status;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }





    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }



    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }
}
