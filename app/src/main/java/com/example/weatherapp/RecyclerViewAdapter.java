package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DataModel2> data;

    public RecyclerViewAdapter(Context context, ArrayList<DataModel2> data) {
        this.context = context;
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTv;
        private ImageView smallIconView;
        private TextView smallTempTv;

        public ViewHolder(View itemView) {
            super(itemView);

            dateTv = itemView.findViewById(R.id.dateTv);
            smallIconView = itemView.findViewById(R.id.smallIconView);
            smallTempTv = itemView.findViewById(R.id.smallTempTv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel2 item = data.get(position);

        // Set the data to the views in the item layout
        holder.dateTv.setText(item.getDate());
        Picasso.get().load(item.getImageUrl()).into(holder.smallIconView);
        holder.smallTempTv.setText(item.getTemperature());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setPosts(ArrayList<DataModel2> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
