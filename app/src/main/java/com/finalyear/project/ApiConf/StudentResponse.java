package com.finalyear.project.ApiConf;

import com.finalyear.project.alumnitracerData.Student;

public class StudentResponse {

    private String message;
    private Student student;
    private String status;

    public String getMessage() {
        return message;
    }

    public Student getStudent() {
        return student;
    }

    public String getStatus() {
        return status;
    }
}
