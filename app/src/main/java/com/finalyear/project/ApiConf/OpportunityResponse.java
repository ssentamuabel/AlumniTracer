package com.finalyear.project.ApiConf;

import com.finalyear.project.alumnitracerData.Opportunity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpportunityResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;
    @SerializedName("opportunity")
    private Opportunity opportunity;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Opportunity getOpportunity() {
        return opportunity;
    }

    public void setOpportunity(Opportunity opportunity) {
        this.opportunity = opportunity;
    }
}
