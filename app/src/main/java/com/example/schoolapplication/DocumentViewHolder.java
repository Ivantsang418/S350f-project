package com.example.schoolapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DocumentViewHolder extends RecyclerView.ViewHolder  {
    TextView fileNameView;
    public DocumentViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        fileNameView = itemView.findViewById(R.id.filename);
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
