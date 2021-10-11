package com.example.techabet_task.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techabet_task.R;
import com.example.techabet_task.models.LocationDataModel;

import java.util.ArrayList;
import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.ViewHolder> {

    Context context;
    List<LocationDataModel> list=new ArrayList<>();
    public LocationListAdapter(Context context, List<LocationDataModel> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        LocationDataModel data=list.get(i);
        viewHolder.timeTV.setText(data.getTimeStr());
        viewHolder.longTV.setText(data.getLongStr());
        viewHolder.latTV.setText(data.getLatStr());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView timeTV;
        TextView longTV;
        TextView latTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTV=itemView.findViewById(R.id.timeTV);
            longTV=itemView.findViewById(R.id.longTV);
            latTV=itemView.findViewById(R.id.latTV);
        }
    }


}
