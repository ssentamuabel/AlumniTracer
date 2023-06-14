package com.finalyear.project.ApiConf;

import com.finalyear.project.alumnitracerData.Opportunity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpportunitiesResponse {


    @SerializedName("opportunity")
    private List<Opportunity> opportunities;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    public List<Opportunity> getOpportunities() {
        return opportunities;
    }

    public void setOpportunities(List<Opportunity> opportunities) {
        this.opportunities = opportunities;
    }

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
}
