package com.example.schoolapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    Spinner spinner;
    Button button;
    FirebaseFirestore db;
    Snackbar mySnackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        if (pref.contains("is_login") && pref.getBoolean("is_login", false)) {
            if (pref.contains("user_type") && pref.getString("user_type", "").equals("student")) {
                Intent i = new Intent(LoginActivity.this, StudentActivity.class);
                startActivity(i);
            }
        }

        setContentView(R.layout.activity_login);
        usernameEditText = (EditText) findViewById(R.id.editTextUsername);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
        spinner = (Spinner) findViewById(R.id.spinnerUserType);
        button = (Button) findViewById(R.id.buttonLogin);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_type, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String username = String.valueOf(usernameEditText.getText());
                String password = String.valueOf(passwordEditText.getText());
                String usertype = spinner.getSelectedItem().toString();
                if (username.equals("") || password.equals("")) {
                    return;
                }
                Log.d(TAG, username + " " +  password + " " + usertype);
                DocumentReference docRef = db.collection("accounts").document(username);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                if (document.getData().get("usertype").toString().equals(usertype) &&
                                        document.getData().get("password").toString().equals(password) ) {

                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putBoolean("is_login", true);
                                    editor.putString("user_type", usertype);
                                    editor.putString("user_name", username);
                                    editor.apply();
                                    mySnackbar = Snackbar.make(v, "Login success", 100);
                                    mySnackbar.show();
                                    if (usertype.equals("student")) {
                                        Intent i = new Intent(LoginActivity.this, StudentActivity.class);
                                        startActivity(i);
                                    } else if (usertype.equals("teacher")) {
                                        Intent i = new Intent(LoginActivity.this, TeacherActivity.class);
                                        startActivity(i);
                                    }
                                } else {
                                    mySnackbar = Snackbar.make(v, "Incorrect username or password", 100);
                                    mySnackbar.show();
                                }

                            } else {
                                Log.d(TAG, "No such username");
                                mySnackbar = Snackbar.make(v, "Incorrect username or password", 100);
                                mySnackbar.show();
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
//                db.collection("accounts")
//                        .whereEqualTo("password", "1234")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        Log.d(TAG, document.getId() + " => " + document.getData());
//                                    }
//                                } else {
//                                    Log.d(TAG, "Error getting documents: ", task.getException());
//                                }
//                            }
//                        });
                Log.d("BUTTONS", "User tapped the Supabutton");
            }
        });
    }


}