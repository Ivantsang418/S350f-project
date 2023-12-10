package com.example.schoolapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentInfoAdapter extends RecyclerView.Adapter<StudentInfoViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<StudentInfoItem> items;
    public StudentInfoAdapter(Context context, List<StudentInfoItem> items, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.items = items;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public StudentInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentInfoViewHolder(LayoutInflater.from(context).inflate(R.layout.student_info_item_view, parent, false), recyclerViewInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StudentInfoViewHolder holder, int position) {
        holder.nameView.setText("Name: " + items.get(position).getName());
        holder.idView.setText("Student ID: " + items.get(position).getStudentId());
        holder.emailView.setText("Email: " + items.get(position).getEmail());
        holder.phoneView.setText("Phone: " + items.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
