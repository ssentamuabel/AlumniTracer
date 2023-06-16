package com.finalyear.project.ApiConf;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {


    @Headers("Content-Type: application/json")
    @POST("Opportunity/AddOpportunity")
    Call<OpportunityResponse> addOpportunity(@Body JsonObject body );

    @Headers("Content-Type: application/json")
    @GET("Student/GetStudents")
    Call<StudentsResponse> getStudents(@Header("Authorization") String authorization);

    @Headers("Content-Type: application/json")
    @GET("Opportunity/GetOpportunities")
    Call<OpportunitiesResponse> getOpportunities(@Header("Authorization") String authorization);




    @Headers("Content-Type: application/json")
    @POST("Users/LoginUser")
    Call<UserResponse> login(@Body JsonObject body );

    @Headers("Content-Type: application/json")
    @POST("Student/RegisterStudent")
    Call<StudentResponse>RegisterStudent(@Body JsonObject body);



}
