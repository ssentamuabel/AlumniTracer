package com.finalyear.project.alumnitracer;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.finalyear.project.ApiConf.ApiInterface;
import com.finalyear.project.ApiConf.OpportunitiesResponse;
import com.finalyear.project.alumnitracerData.Opportunity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OpportunityFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ApiInterface apiInterface;


    private List<Opportunity> list = new ArrayList<Opportunity>();


    public OpportunityFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opportunity, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), OpportunityForm.class);
                startActivity(intent);
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

        SharedPreferences sp = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");

        Call<OpportunitiesResponse> call = apiInterface.getOpportunities("Bearer "+token);

        call.enqueue(new Callback<OpportunitiesResponse>() {
            @Override
            public void onResponse(Call<OpportunitiesResponse> call, Response<OpportunitiesResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (! response.body().getOpportunities().isEmpty()) {
                            Log.d("TAG", "onResponse: " + response.body().getOpportunities().get(0).getTitle());

                            list = response.body().getOpportunities();

                            Log.d("TAG", "list Item: " + response.body().getOpportunities().get(0).getTitle());

                            recyclerView = (RecyclerView) view.findViewById(R.id.opp_recyclerView);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));


                            adapter = new OpportunityAdaptor(list, container.getContext());

                            recyclerView.setAdapter(adapter);

                        }

                    }
                }else{

                    Log.d("TAG", "onResponse: Something went wrong ");
                }

            }

            @Override
            public void onFailure(Call<OpportunitiesResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Check your network connection "+ t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



        // Inflate the layout for this fragment
        return view;
    }
}