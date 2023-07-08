package com.finalyear.project.alumnitracer;





import android.content.ClipData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import com.finalyear.project.ApiConf.ApiInterface;
import com.finalyear.project.ApiConf.StudentResponse;
import com.finalyear.project.ApiConf.StudentsResponse;
import com.finalyear.project.alumnitracerData.Student;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkFragment extends Fragment {

    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private ApiInterface apiInterface;

    private List<Student>students = new ArrayList<Student>();
    String msg;



    public NetworkFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_network, container, false);


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


        Call<StudentsResponse> call = apiInterface.getStudents("Bearer "+token);

        call.enqueue(new Callback<StudentsResponse>() {
            @Override
            public void onResponse(Call<StudentsResponse> call, Response<StudentsResponse> response) {
                if(response.body() !=null){
                    if(response.body() != null){
                        if(! response.body().getStudents().isEmpty()){
                            Log.d("TAG", "onResponse: "+ response.body().getStudents().get(0).getHall());



                            students = response.body().getStudents();


                            recyclerView = (RecyclerView) view.findViewById(R.id.network_recyclerView);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));


                            adapter = new StudentAdapter(students, container.getContext());

                            recyclerView.setAdapter(adapter);


                        }
                    }
                }else{
                    Log.d("TAG", "Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<StudentsResponse> call, Throwable t) {
                Log.d("TAG", "onFailure "+ t.getMessage());
                t.printStackTrace();
            }
        });

        SearchView searchView = (SearchView) view.findViewById(R.id.network_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });



        // Inflate the layout for this fragment
        return view;
    }





}