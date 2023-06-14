package com.finalyear.project.alumnitracer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.finalyear.project.alumnitracer.databinding.ActivityOpportunityFormBinding;

public class OpportunityForm extends AppCompatActivity {
    private ActivityOpportunityFormBinding binding;
    Button submit_opp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunity_form);

        submit_opp = (Button) findViewById(R.id.opp_submit);

        submit_opp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}