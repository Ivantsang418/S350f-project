package com.example.schoolapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.StorageReference;

import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<StorageReference> items;
    public DocumentAdapter(Context context, List<StorageReference> items, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.items = items;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public DocumentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DocumentViewHolder(LayoutInflater.from(context).inflate(R.layout.document_item_view, parent, false), recyclerViewInterface);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DocumentViewHolder holder, int position) {
        holder.fileNameView.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
