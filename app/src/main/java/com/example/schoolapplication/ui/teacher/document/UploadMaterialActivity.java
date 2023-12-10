package com.example.schoolapplication.ui.teacher.document;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

public class UploadMaterialActivity extends AppCompatActivity {

    Button selectFileButton, uploadFileButton;
    TextView filePathView;
    Uri fileUri;
    Snackbar mySnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_material);

        selectFileButton = findViewById(R.id.buttonSelectFileSubmitMaterial);
        uploadFileButton = findViewById(R.id.buttonUploadSubmitMaterial);
        filePathView = findViewById(R.id.textViewFileUploadMaterial);

        selectFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        uploadFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileUri != null) {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference();
                    StorageReference riversRef = storageRef.child("courese_materials/"+getFileName(fileUri));
                    UploadTask uploadTask = riversRef.putFile(fileUri);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            mySnackbar = Snackbar.make(view, "Upload unsuccess", 100);
                            mySnackbar.show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            filePathView.setText("File");
                            fileUri = null;
                            mySnackbar = Snackbar.make(view, "Upload success", 100);
                            mySnackbar.show();
                        }
                    });
                } else {
                    mySnackbar = Snackbar.make(view, "Please select a file", 100);
                    mySnackbar.show();
                }

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference listRef = storage.getReference().child("assignments/");
                ArrayList<StorageReference> files = new ArrayList<>();
                listRef.listAll()
                        .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                            @Override
                            public void onSuccess(ListResult listResult) {
                                files.addAll(listResult.getItems());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Uh-oh, an error occurred!
                            }
                        });
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 100);
        } catch (Exception exception) {
            Toast.makeText(this, "Please install a file manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            fileUri = data.getData();
            String path = fileUri.getPath();
            File file = new File(path);
            filePathView.setText("File path: " + path);
        } else {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}