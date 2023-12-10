package com.example.schoolapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentInfoViewHolder extends RecyclerView.ViewHolder {
    TextView nameView, idView, emailView, phoneView;
    public StudentInfoViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        nameView = itemView.findViewById(R.id.notificationTitle);
        idView = itemView.findViewById(R.id.createdAt);
        emailView = itemView.findViewById(R.id.notificationMessage);
        phoneView = itemView.findViewById(R.id.phone);

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
