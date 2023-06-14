package com.finalyear.project.alumnitracer;

public class StudentModel {
    private String name;
    private String course;
    private String status;
    private String contact;

    public StudentModel(String name, String course, String status, String contact) {
        this.name = name;
        this.course = course;
        this.status = status;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
