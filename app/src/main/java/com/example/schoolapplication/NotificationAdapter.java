package com.example.schoolapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;

    List<NotificationItem> items;
    public NotificationAdapter(Context context, List<NotificationItem> items, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.items = items;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_item_view, parent, false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.titleView.setText(items.get(position).getTitle());
        holder.messageView.setText(items.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
