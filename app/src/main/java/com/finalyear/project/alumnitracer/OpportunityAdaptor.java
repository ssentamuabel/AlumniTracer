package com.finalyear.project.alumnitracer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalyear.project.alumnitracerData.Opportunity;

import java.util.ArrayList;
import java.util.List;

public class OpportunityAdaptor   extends RecyclerView.Adapter<OpportunityAdaptor.ViewHolder>  {
    private List<Opportunity> opportunityList;
    private Context context;


    public OpportunityAdaptor(List<Opportunity> opportunityList, Context context) {

        this.opportunityList = opportunityList;
        this.context = context;
    }

    @NonNull
    @Override
    public OpportunityAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.opportunity_row, parent, false);
        return new OpportunityAdaptor.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OpportunityAdaptor.ViewHolder holder, int position) {

        Opportunity opportunity = opportunityList.get(position);
        holder.dueDate.setText(opportunity.getDueDate());
        holder.category.setText(opportunity.getCategory());
        holder.title.setText(opportunity.getTitle());
        holder.description.setText(opportunity.getDescription());
        holder.contact.setText(opportunity.getContact());
        holder.target.setText(opportunity.getTarget());

    }

    @Override
    public int getItemCount() {
        return opportunityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView  dueDate, category, title, description, contact, target;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dueDate = (TextView) itemView.findViewById(R.id.opp_due_date);
            category = (TextView) itemView.findViewById(R.id.opport_category);
            title = (TextView) itemView.findViewById(R.id.opport_title);
            description = (TextView) itemView.findViewById(R.id.opp_desc);
            contact = (TextView) itemView.findViewById(R.id.opp_contacts);
            target = (TextView) itemView.findViewById(R.id.opport_target);

        }
    }
}
