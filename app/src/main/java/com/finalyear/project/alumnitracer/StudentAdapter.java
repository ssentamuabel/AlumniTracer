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

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> implements Filterable {


    private List<Student> studentList;
    private List<Student>studentListFull;

    private Context context;


    public StudentAdapter(List<Student> studentList, Context context) {

        this.studentList = studentList;
        this.studentListFull = new ArrayList<>(studentList);
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
        holder.profileTxt.setText(student.getUser().getFirstName().charAt(0)+ ""+student.getUser().getLastName().charAt(0));
        holder.reg.setText(student.getRegNo().charAt(0)+ ""+student.getRegNo().charAt(1));

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, course, contact, status, profileTxt, reg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileTxt = (TextView) itemView.findViewById(R.id.tv_prefix);
            name = (TextView) itemView.findViewById(R.id.name);
            course = (TextView) itemView.findViewById(R.id.course);
            contact = (TextView) itemView.findViewById(R.id.contact);
            status = (TextView) itemView.findViewById(R.id.status);
            reg = (TextView) itemView.findViewById(R.id.network_reg);

        }
    }

    @Override
    public Filter getFilter() {
        return studentFilter;
    }

    private Filter studentFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Student> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(studentListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Student student: studentListFull ){
                    if(student.getUser().getFirstName().contains(filterPattern) || student.getRegNo().contains(filterPattern)){
                        filteredList.add(student);
                    }
                }
            }

            FilterResults results  = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            studentList.clear();
            studentList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };



}
