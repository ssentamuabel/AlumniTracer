package com.finalyear.project.alumnitracerData;

import com.google.gson.annotations.SerializedName;

public class Opportunity {

    @SerializedName("dueDate")
    private String dueDate;

    @SerializedName("id")
    private String id;

    @SerializedName("presenterId")
    private String studentId;

    @SerializedName("category")
    private String category;

    @SerializedName("status")
    private String status;

    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    private String target;
    @SerializedName("contact")
    private String contact;




    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
