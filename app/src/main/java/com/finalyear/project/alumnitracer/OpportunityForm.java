package com.finalyear.project.alumnitracer;

import static com.finalyear.project.alumnitracer.RegisterFragment.validateName;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.finalyear.project.ApiConf.ApiInterface;
import com.finalyear.project.ApiConf.OpportunityResponse;
import com.finalyear.project.alumnitracer.databinding.ActivityOpportunityFormBinding;
import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class OpportunityForm extends AppCompatActivity {

    private ActivityOpportunityFormBinding  binding;
    String category, title, target, contact, description, dueDate, personalId;
    Button submit_opp ;
    private ApiInterface apiInterface;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        binding = ActivityOpportunityFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        // category adaptor

        ArrayAdapter<CharSequence> categoryAdapter =  ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.inputOppCategory.setAdapter(categoryAdapter);

        binding.inputOppCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d("LOG: ", " To console: "+parent.getItemAtPosition(position).toString());

                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // target adaptor

        ArrayAdapter<CharSequence> targetAdapter =  ArrayAdapter.createFromResource(this, R.array.target_array, android.R.layout.simple_spinner_item);

        targetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.inputOppTarget.setAdapter(targetAdapter);

        binding.inputOppTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d("LOG: ", " To console: "+parent.getItemAtPosition(position).toString());

                target = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // date picker

      binding.inputOppDueDate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


              DatePickerDialog datePickerDialog= new DatePickerDialog(OpportunityForm.this, new DatePickerDialog.OnDateSetListener() {
                  @Override
                  public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                      String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                      binding.inputOppDueDate.setText(selectedDate);
                  }
              },year, month, dayOfMonth);
              // Show the Date Picker Dialog
              datePickerDialog.show();
          }
      });
        binding.oppSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.inputOpportTitle.setError(null);
                binding.inputOppContact.setError(null);
                binding.inputOppDescription.setError(null);

                title = binding.inputOpportTitle.getText().toString().trim();
                contact = binding.inputOppContact.getText().toString().trim();
                description = binding.inputOppDescription.getText().toString().trim();

                if(formValidation() == false){

                    Toast.makeText(OpportunityForm.this, "Thanks for registering", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(OpportunityForm.this, "You have to fill all the fields", Toast.LENGTH_SHORT).show();

                    SharedPreferences sp = OpportunityForm.this.getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    String token = sp.getString("token", "");

                    JsonObject dataObject = new JsonObject();
                    JsonObject oppObj = new JsonObject();




                    oppObj.addProperty("token", token);
                    oppObj.addProperty("target", target);
                    oppObj.addProperty("category",category);
                    oppObj.addProperty("description", description);
                    oppObj.addProperty("dueDate", dueDate);
                    oppObj.addProperty("title", title);
                    oppObj.addProperty("contact", contact);

                    addOpportunity(dataObject);

                }










            }
        });

    }

    private void addOpportunity(JsonObject jsonObject){

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



        Call<OpportunityResponse> call = apiInterface.addOpportunity(jsonObject);

        call.enqueue(new Callback<OpportunityResponse>() {
            @Override
            public void onResponse(Call<OpportunityResponse> call, Response<OpportunityResponse> response) {
                if(response.isSuccessful()){
                    if(response.body()!= null){
                        if(response.body().getStatus().equals("200")){
                            Toast.makeText(OpportunityForm.this, "Thanks for sharing", Toast.LENGTH_SHORT);
                            finish();
                        }else{
                            Toast.makeText(OpportunityForm.this, "Something is wrong with the post", Toast.LENGTH_SHORT);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OpportunityResponse> call, Throwable t) {

            }
        });
    }

    private Boolean formValidation(){

        Boolean result = false;

        if(TextUtils.isEmpty(title) || !validateName(title)){
            binding.inputOpportTitle.setError("The title is needed");
            binding.inputOpportTitle.requestFocus();
            result = result || true;
        }

        if(TextUtils.isEmpty(description) || !validateDescription(description) ){
            binding.inputOppDescription.setError("The description should be between 60 and 160 characters");
            binding.inputOppDescription.requestFocus();
            result = result || true;
        }

        if(TextUtils.isEmpty(contact) || !validateContact(contact)){
            binding.inputOppContact.setError("Contact details are needed, email or phone or address or all");
            binding.inputOppContact.requestFocus();
            result = result || true;
        }

        if(TextUtils.isEmpty(dueDate)){
            result = result || true;
        }

        if( !category.equals("Select category")){
            if(category.equals("INTERN")){
                category="Intern";
            }
            if(category.equals("GRADUATE_TRAINING")){
                category="GraduateTraining";
            }
            if(category.equals("JOB")){
                category="Payable";
            }
        } else{result = result || true;}

        if( !target.equals("Select Target group")){
            if(category.equals("ANY")){
                target="Any";
            }
            if(category.equals("UNDER_GRADUATE")){
                target="Undergraduate";
            }
            if(category.equals("GRADUATE")){
                target="Graduate";
            }
        } else{result = result || true;}


        return result;
    }

    private Boolean validateDescription(String des){
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z]).{50,160}$");
        Matcher matcher = pattern.matcher(des);
        return matcher.matches();
    }

    private Boolean validateContact(String contact){
        String trimmedText = contact.replaceAll("\\r?\\n", "");
        Pattern pattern = Pattern.compile("^(?=.*(?:\\b\\d{10}\\b|\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b|\\b[A-Za-z0-9\\s.,-]+\\b)).*$");
        Matcher matcher = pattern.matcher(trimmedText);
        return matcher.matches();
    }
}