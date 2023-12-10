package com.example.schoolapplication.ui.teacher.academic;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.schoolapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditAcademicActivity extends AppCompatActivity {

    TextView nameView, idView;
    EditText gpaEditView;
    Button saveButton;
    Snackbar mySnackbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_academic);

        String username = getIntent().getStringExtra("USERNAME");

        nameView = (TextView) findViewById(R.id.textViewAcademicStudentName);
        idView = (TextView) findViewById(R.id.textViewAcademicID);
        gpaEditView = (EditText) findViewById(R.id.editTextGPA);
        saveButton = (Button) findViewById(R.id.buttonSaveAcademic);

        if (username != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("accounts").document(username);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            String name = document.getData().get("firstName") + " " + document.getData().get("lastName");
                            nameView.setText((CharSequence) name);
                            idView.setText((CharSequence) document.getData().get("studentId"));
                            gpaEditView.setText(document.getData().get("gpa").toString());
                        } else {
                            Log.d(TAG, "No such username");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                if (username != null) {
                    db.collection("accounts").document(username).update("gpa", Double.valueOf(gpaEditView.getText().toString()));
                    gpaEditView.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    mySnackbar = Snackbar.make(view, "Update success", 200);
                    mySnackbar.show();
                }
            }
        });
    }
}