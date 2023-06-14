package com.finalyear.project.alumnitracer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.finalyear.project.ApiConf.ApiInterface;
import com.finalyear.project.ApiConf.StudentResponse;
import com.finalyear.project.ApiConf.UserResponse;
import com.finalyear.project.alumnitracer.databinding.FragmentLoginBinding;
import com.finalyear.project.alumnitracerData.User;
import com.google.gson.JsonObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    String username, password, msg;
    User user;
    SharedPreferences sp;

    private ApiInterface apiInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view =  binding.getRoot();



       // checkUser();


        binding.signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 RegisterFragment registerFragment =new RegisterFragment();
                 getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, registerFragment).commit();

            }
        });


        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = binding.username.getText().toString();
                password = binding.password.getText().toString();
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(getContext(), "Feel the fields", Toast.LENGTH_SHORT).show();
                }else{

                    JsonObject dataObject = new JsonObject();

                    dataObject.addProperty("username", username);
                    dataObject.addProperty("password", password);

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


                    Call<UserResponse> call  = apiInterface.login(dataObject);

                    call.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                            Log.d("DEBug", "onResponse: " + response.body().getUser().getFirstName());


                            if(!response.isSuccessful()){
                                msg ="Some thing went wrong";

                            }

                            Log.d("DEBug", "onResponse: " + response.body().getUser().getFirstName());

                            user = response.body().getUser();
                            Log.d("DEBug", "User: " + user.getFirstName());

                            System.out.println(response);
                            System.out.println(user.getGender());


                            // storing the token and the username to  the shared preferences
                            sp = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor  = sp.edit();
                            editor.putString("token", response.body().getUser().getToken());
                            editor.putString("username", response.body().getUser().getUsername());
                            editor.commit();



                            Intent intent = new Intent(getContext().getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            msg="The process failed";
                        }
                    });




                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
                }
            }
        });



        return view;
    }




    private void checkUser(){

        SharedPreferences sp = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        if(!token.isEmpty()){
            Intent intent = new Intent(getContext().getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }


}