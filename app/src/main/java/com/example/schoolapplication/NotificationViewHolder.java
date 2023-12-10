package com.example.schoolapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationViewHolder extends RecyclerView.ViewHolder  {
    TextView titleView, messageView;
    public NotificationViewHolder(@NonNull View itemView,  RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        titleView = itemView.findViewById(R.id.notificationTitle);
        messageView = itemView.findViewById(R.id.notificationMessage);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            }
        });
    }
}
