package com.finalyear.project.alumnitracer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterFragment extends Fragment {

    String firstName, lastName, regNo, status, course, hall,email, username,dob,  password;
    String  contact, maritialStatus, residence, homeTown, gender, user, comfirmPassword;

    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
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

        //  status drop down

        ArrayAdapter<CharSequence> statusAdapter =  ArrayAdapter.createFromResource(getContext(), R.array.status_array, android.R.layout.simple_spinner_item);

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.status.setAdapter(statusAdapter);

        binding.status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d("LOG: ", " To console: "+parent.getItemAtPosition(position).toString());

                status = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // maritaial drop down

        ArrayAdapter<CharSequence> maritialAdapter =  ArrayAdapter.createFromResource(getContext(), R.array.maritial_array, android.R.layout.simple_spinner_item);

        maritialAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.maritialStatus.setAdapter(maritialAdapter);

        binding.maritialStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d("LOG: ", " To console: "+parent.getItemAtPosition(position).toString());
                maritialStatus = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // hall drop down

        ArrayAdapter<CharSequence> hallAdapter =  ArrayAdapter.createFromResource(getContext(), R.array.hall_array, android.R.layout.simple_spinner_item);

        hallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.hall.setAdapter(hallAdapter);

        binding.hall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d("LOG: ", " To console: "+parent.getItemAtPosition(position).toString());
                hall = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        // Gender drop down
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(getContext(), R.array.gender_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.gender.setAdapter(adapter);

        binding.gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d("LOG: ", " To console: "+parent.getItemAtPosition(position).toString());

                gender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // date picker

        binding.dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create a date picker dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        binding.dateOfBirth.setText(selectedDate);
                    }
                },  year, month, dayOfMonth);

                // Show the Date Picker Dialog
                datePickerDialog.show();
            }
        });



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
                binding.firstname.setError(null);
                binding.lastname.setError(null);
                binding.regNo.setError(null);
                binding.course.setError(null);
                binding.dateOfBirth.setError(null);
                binding.email.setError(null);
                binding.username.setError(null);
                binding.password.setError(null);
                binding.residence.setError(null);
                binding.homeDistrict.setError(null);
                binding.contact.setError(null);
                binding.comfirmPassword.setError(null);

               firstName = binding.firstname.getText().toString().trim();
               lastName = binding.lastname.getText().toString().trim();
               regNo = binding.regNo.getText().toString().trim();
               course = binding.course.getText().toString().trim();
                dob  = binding.dateOfBirth.getText().toString().trim();
               email = binding.email.getText().toString().trim();
               username = binding.username.getText().toString().trim();
               password = binding.password.getText().toString().trim();
               residence = binding.residence.getText().toString().trim();
               homeTown = binding.homeDistrict.getText().toString().trim();
               contact = binding.contact.getText().toString().trim();
               comfirmPassword =binding.comfirmPassword.getText().toString().trim();


                formValidation();
                Toast.makeText(getContext(), "Result: "+ formValidation().toString(), Toast.LENGTH_SHORT).show();


               if(!formValidation()){
                   Toast.makeText(getContext(), "Form is missing some values, please try again.", Toast.LENGTH_SHORT).show();
               }else{

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

               binding.firstname.setText(null);
              binding.lastname.setText("");
              binding.regNo.setText("");
              binding.course.setText("");
              binding.dateOfBirth.setText("");
              binding.email.setText("");
              binding.username.setText("");
              binding.password.setText("");
              binding.residence.setText("");
              binding.homeDistrict.setText("");
              binding.contact.setText("");


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


    private Boolean formValidation(){
        Boolean result = false;
        if(TextUtils.isEmpty(firstName) || !validateName(firstName)){
            binding.firstname.setError("First name is invalid");
            binding.firstname.requestFocus();

            result = result || true;
        }

        if(TextUtils.isEmpty(lastName) || !validateName(lastName)){
            binding.lastname.setError("Last name is invalid");
            binding.lastname.requestFocus();

            result = result || true;
        }



        if(TextUtils.isEmpty(residence) || !validateName(residence)){
            homeTown="UNKNOWN";

            result = result || true;
        }


        if(TextUtils.isEmpty(homeTown) || !validateName(homeTown)){
            homeTown="UNKNOWN";

            result = result || true;
        }

        if(TextUtils.isEmpty(course) || !validateName(course)){
            binding.course.setError("Invalid input");
            binding.course.requestFocus();

            result = result || true;
        }

        if(TextUtils.isEmpty(contact) || !validatePhone(contact)){
            binding.contact.setError("Contact is invalid");
            binding.contact.requestFocus();

            result = result || true;
        }

        if(hall.equals("Select Hall") ){
            hall = "KULUBYA";
        }

        if(maritialStatus.equals("Select Maritial Status") ){
            maritialStatus = "SINGLE";
        }

        if(gender.equals("Select Gender")){

            result = result || true;
        }

        if(status.equals("Student/Alumunus")){

            result = result || true;
        }

        if(!validateEmail(email)){
            binding.email.setError("Email is invalid");
            binding.email.requestFocus();
            result = result || true;
        }
        if(!validatePassword(password)){
            binding.password.setError("The password is too simple");
            binding.password.requestFocus();
            result = result || true;
        }


        if(!comfirmPassword.equals(password) ){
            binding.comfirmPassword.setError("The passwords do not match");
            binding.comfirmPassword.requestFocus();
            result = result || true;
        }

        if(TextUtils.isEmpty(username) || !validateName(username) ){
            binding.username.setError("Username is not valid");
            binding.username.requestFocus();
            result = result || true;
        }



        return result;
    }


    public static Boolean validateName(String name){
        Pattern pattern = Pattern.compile("^[A-Za-z]+(?:[ '-][A-Za-z]+)*$");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    private Boolean validatePhone(String phone){
        Pattern pattern = Pattern.compile("^(?:\\+?\\d{1,3}[-.\\s]?)?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private Boolean validateReg(String reg){
        Pattern pattern = Pattern.compile("^(?:\\+?\\d{1,3}[-.\\s]?)?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}$");
        Matcher matcher = pattern.matcher(reg);
        return matcher.matches();
    }

    private Boolean validateEmail(String email){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private Boolean validatePassword(String password){
        Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }



}