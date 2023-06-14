package com.finalyear.project.ApiConf;

import com.finalyear.project.alumnitracerData.Student;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentsResponse {

    @SerializedName("students")
    private List<Student> students;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;


    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getStatus() {
        return status;
    }
}
