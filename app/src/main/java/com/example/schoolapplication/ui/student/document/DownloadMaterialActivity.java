package com.example.schoolapplication.ui.student.document;

import static android.content.ContentValues.TAG;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.example.schoolapplication.DocumentAdapter;
import com.example.schoolapplication.R;
import com.example.schoolapplication.RecyclerViewInterface;
import com.example.schoolapplication.StudentInfoAdapter;
import com.example.schoolapplication.StudentInfoItem;
import com.example.schoolapplication.ui.teacher.academic.EditAcademicActivity;
import com.example.schoolapplication.ui.teacher.academic.TeacherAcademicFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DownloadMaterialActivity extends AppCompatActivity implements RecyclerViewInterface {
    List<StorageReference> items;
    RecyclerView recyclerView;
    Snackbar mySnackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_material);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDownloadMaterials);
    }

    public void onStart() {
        super.onStart();

        items = new ArrayList<StorageReference>();
        readData(new FirestoreCallback() {
            @Override
            public void onCallback(List<StorageReference> list) {
                setRecyclerView(recyclerView, list);
            }
        });
    }

    private void readData(FirestoreCallback firestoreCallback) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference listRef = storage.getReference().child("courese_materials/");
        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        items.addAll(listResult.getItems());
                        firestoreCallback.onCallback(items);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                        mySnackbar = Snackbar.make(recyclerView, "Error occurred",  Snackbar.LENGTH_LONG);
                        mySnackbar.show();
                    }
                });
    }

    public void onItemClick(int position) {
        Log.d(TAG, "Item click position: " + items.get(position).getName());
        StorageReference storageReference = items.get(position);
        String name = storageReference.getName().split("\\.")[0];
        String extension = storageReference.getName().split("\\.")[1];
        Log.d(TAG, "name: " + name);
        Log.d(TAG, "extension: " + extension);

//        File rootPath = new File(Environment.getExternalStorageDirectory(), "school_app_download");
//        if(!rootPath.exists()) {
//            rootPath.mkdirs();
//        }
//        final File localFile = new File(rootPath,storageReference.getName());
//        storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                Log.e("firebase ",";local tem file created  created " +localFile.toString());
//                //  updateDb(timestamp,localFile.toString(),position);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.e("firebase ",";local tem file not created  created " +exception.toString());
//            }
//        });

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                Log.d(TAG, "url: " + url);
                downloadFiles(getApplicationContext(), name, "."+extension, DIRECTORY_DOWNLOADS,url);
                mySnackbar = Snackbar.make(recyclerView, "Download success",  Snackbar.LENGTH_LONG);
                mySnackbar.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mySnackbar = Snackbar.make(recyclerView, "Error occurred", Snackbar.LENGTH_LONG);
                mySnackbar.show();
            }
        });


        //Intent intent = new Intent(this, EditAcademicActivity.class);
        //intent.putExtra("USERNAME", items.get(position).getUsername());
        //startActivity(intent);
    }


    public void downloadFiles(Context context, String fileName, String fileExtension, String destinationDirectory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.
                getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension);
        downloadManager.enqueue(request);
    }


    private interface FirestoreCallback {
        void onCallback(List<StorageReference> list);
    }

    private void setRecyclerView(RecyclerView recyclerView, List<StorageReference> items) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DocumentAdapter(this.getApplicationContext(), items, this));
    }

}