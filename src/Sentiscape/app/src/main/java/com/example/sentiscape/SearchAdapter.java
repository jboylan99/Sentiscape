package com.example.sentiscape;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private ArrayList<FirebaseModel> firebaseModelArrayList;
    private Context context;

    public SearchAdapter(ArrayList<FirebaseModel> firebaseModelArrayList, Context context) {
        this.firebaseModelArrayList = firebaseModelArrayList;
        this.context = context;
    }

    public void filterList(ArrayList<FirebaseModel> filterList) {
        firebaseModelArrayList = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FirebaseModel model = firebaseModelArrayList.get(position);
        holder.nameOfUser.setText(model.getName());
        holder.statusOfUser.setText(model.getStatus());

        if (model.getStatus().equals("Online")) {
            holder.statusOfUser.setText(model.getStatus());
            holder.statusOfUser.setTextColor(Color.GREEN);
        } else {
            holder.statusOfUser.setText(model.getStatus());
            holder.statusOfUser.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return firebaseModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameOfUser, statusOfUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameOfUser = itemView.findViewById(R.id.nameOfUser);
            statusOfUser = itemView.findViewById(R.id.statusOfUser);
        }
    }
}
