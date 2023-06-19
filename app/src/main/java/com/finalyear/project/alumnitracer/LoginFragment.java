package com.finalyear.project.alumnitracer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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



        checkUser();


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

                binding.username.setError(null);
                binding.password.setError(null);

                username = binding.username.getText().toString().trim();
                password = binding.password.getText().toString().trim();
                if(validation()){
                    Toast.makeText(getContext(), "Feel the fields", Toast.LENGTH_SHORT).show();

                }else{

                    JsonObject dataObject = new JsonObject();

                    dataObject.addProperty("username", username);
                    dataObject.addProperty("password", password);

                    login(dataObject);




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

    private Boolean validation(){
        Boolean result = false;

        if(username.isEmpty()){
            binding.username.setError("Username is missing");
            binding.username.requestFocus();
            result = result || true;
        }

        if(password.isEmpty()){
            binding.password.setError("Password is missing");
            binding.password.requestFocus();
            result = result || true;
        }
        return result;
    }


    private void login(JsonObject dataObject){

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



                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getUser() == null) {

                            Toast.makeText(getContext().getApplicationContext(), "Username and password do not match", Toast.LENGTH_LONG);

                        }else{
                            Log.d("DEBug", "onResponse: " + response.body().getUser().getFirstName());




                            Log.d("DEBug", "onResponse: " + response.body().getUser().getFirstName());

                            user = response.body().getUser();
                            Log.d("DEBug", "User: " + user.getFirstName());




                            // storing the token and the username to  the shared preferences
                            sp = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor  = sp.edit();
                            editor.putString("token", response.body().getUser().getToken());
                            editor.putString("username", response.body().getUser().getUsername());
                            editor.commit();



                            Intent intent = new Intent(getContext().getApplicationContext(), MainActivity.class);
                            startActivity(intent);


                        }
                    }else{
                        msg = response.message();
                    }
                }


            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Check your connection", Toast.LENGTH_LONG).show();
            }
        });


    }

}