package com.finalyear.project.alumnitracer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.finalyear.project.ApiConf.ApiInterface;
import com.finalyear.project.ApiConf.RetrofitClientInstance;
import com.finalyear.project.ApiConf.StudentResponse;
import com.finalyear.project.alumnitracer.databinding.FragmentRegisterBinding;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.time.Year;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterFragment extends Fragment {

    String firstName, lastName, regNo, status, course, hall,email, username,dob,  password;
    String  contact, maritialStatus, residence, homeTown, gender, user;
    private FragmentRegisterBinding binding;
    private ApiInterface apiInterface;
    private Retrofit retrofit ;


    private String msg;
    public RegisterFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        View view = binding.getRoot();


        binding.loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginFragment loginFragment = new LoginFragment();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, loginFragment).commit();

            }
        });

        /**
         * handling user input
         * */

        binding.registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               firstName = binding.firstname.getText().toString();
               lastName = binding.lastname.getText().toString();
               regNo = binding.regNo.getText().toString();
               status = binding.status.getText().toString();
               course = binding.course.getText().toString();
               hall = binding.hall.getText().toString();
               gender = binding.gender.getText().toString();
                dob  = binding.dateOfBirth.getText().toString();
               email = binding.email.getText().toString();
               username = binding.username.getText().toString();
               password = binding.password.getText().toString();
               maritialStatus = binding.maritialStatus.getText().toString();
               residence = binding.residence.getText().toString();
               homeTown = binding.homeDistrict.getText().toString();
               contact = binding.contact.getText().toString();


               if(firstName.isEmpty() || lastName.isEmpty()|| regNo.isEmpty()|| status.isEmpty()|| course.isEmpty()||
                    hall.isEmpty() || gender.isEmpty() ||email.isEmpty() || username.isEmpty() || password.isEmpty()){
                   Toast.makeText(getContext(), "Feel all the fields", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(getContext(), "Thanks for registering", Toast.LENGTH_SHORT).show();
                   JsonObject dataObject = new JsonObject();
                   JsonObject userObj = new JsonObject();

                       userObj.addProperty("firstName", firstName);
                       userObj.addProperty("lastName", lastName);
                       userObj.addProperty("phoneNumber",contact);
                       userObj.addProperty("gender", gender);
                       userObj.addProperty("emailAddress", email);
                       userObj.addProperty("username",username);
                       userObj.addProperty("password", password);

                   dataObject.add("user", userObj);
                   dataObject.addProperty("regNo", regNo);
                   dataObject.addProperty("studentStatus", status);
                   dataObject.addProperty("courseCode", course);
                   dataObject.addProperty("hall", hall);
                   dataObject.addProperty("homeTown", homeTown);
                   dataObject.addProperty("residence", residence);
                   dataObject.addProperty("doB", dob);
                   dataObject.addProperty("maritialStatus", maritialStatus);




                   RegisterUser(dataObject);
               }
            }
        });


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()

                .addInterceptor(loggingInterceptor)
                .build();


        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl("http://172.105.109.154:8080/alumni/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiInterface = retrofit.create(ApiInterface.class);


        return view;
    }

    private void RegisterUser(JsonObject jsonObject){

        Call<StudentResponse>call  = apiInterface.RegisterStudent(jsonObject);

        call.enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {


                if(!response.isSuccessful()){
                    msg ="Some thing went wrong";
                    return ;
                }

                System.out.println(response.body().getMessage());

                LoginFragment loginFragment = new LoginFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, loginFragment).commit();

            }

            @Override
            public void onFailure(Call<StudentResponse> call, Throwable t) {
                msg="The process failed";
            }
        });

    }






}