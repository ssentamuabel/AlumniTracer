package com.finalyear.project.alumnitracer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalyear.project.alumnitracerData.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>  {


    private List<Student> studentList;

    private Context context;


    public StudentAdapter(List<Student> studentList, Context context) {

        this.studentList = studentList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.network_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {

        Student student = studentList.get(position);
        holder.name.setText(student.getUser().getFirstName() + " " + student.getUser().getLastName());
        holder.course.setText(student.getCourse());
        holder.contact.setText(student.getUser().getPhoneNumber());
        holder.status.setText(student.getStatus());

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, course, contact, status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            course = (TextView) itemView.findViewById(R.id.course);
            contact = (TextView) itemView.findViewById(R.id.contact);
            status = (TextView) itemView.findViewById(R.id.status);
        }
    }
}
