package com.example.schoolapplication.ui.student.info;

import static android.content.ContentValues.TAG;

import static com.google.android.gms.common.util.CollectionUtils.mapOf;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.schoolapplication.LoginActivity;
import com.example.schoolapplication.R;
import com.example.schoolapplication.StudentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText emailEditText;
    EditText phoneEditText;
    Button editButton;
    Button saveButton;
    View view;
    FirebaseFirestore db;
    Snackbar mySnackbar;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info, container, false);
        firstNameEditText = (EditText) view.findViewById(R.id.editTextFirstName);
        lastNameEditText = (EditText) view.findViewById(R.id.editTextLastName);
        emailEditText = (EditText) view.findViewById(R.id.editTextEmail);
        phoneEditText = (EditText) view.findViewById(R.id.editTextPhone);
        firstNameEditText.setEnabled(false);
        lastNameEditText.setEnabled(false);
        emailEditText.setEnabled(false);
        phoneEditText.setEnabled(false);

        // load data
        SharedPreferences pref =  this.requireActivity().getSharedPreferences("MyPref", 0);
        String username = pref.getString("user_name","");
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("accounts").document(username);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        firstNameEditText.setText((CharSequence) document.getData().get("firstName"));
                        lastNameEditText.setText((CharSequence) document.getData().get("lastName"));
                        emailEditText.setText((CharSequence) document.getData().get("email"));
                        phoneEditText.setText((CharSequence) document.getData().get("phone"));
                    } else {
                        Log.d(TAG, "No such username");
                        mySnackbar = Snackbar.make(view, "You haven't login", Snackbar.LENGTH_LONG);
                        mySnackbar.show();
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        editButton = (Button) view.findViewById(R.id.buttonEdit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstNameEditText.setEnabled(true);
                lastNameEditText.setEnabled(true);
                emailEditText.setEnabled(true);
                phoneEditText.setEnabled(true);
                saveButton.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.INVISIBLE);
            }
        });

        saveButton = (Button) view.findViewById(R.id.buttonSave);
        saveButton.setVisibility(View.INVISIBLE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInfo(username, firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        phoneEditText.getText().toString());
                mySnackbar = Snackbar.make(view, "Update success", 100);
                mySnackbar.show();
                firstNameEditText.setEnabled(false);
                lastNameEditText.setEnabled(false);
                emailEditText.setEnabled(false);
                phoneEditText.setEnabled(false);
                saveButton.setVisibility(View.INVISIBLE);
                editButton.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void updateInfo(String username, String firstName, String lastName, String email, String phone) {
        db.collection("accounts").document(username).update("firstName", firstName);
        db.collection("accounts").document(username).update("lastName", lastName);
        db.collection("accounts").document(username).update("phone", phone);
        db.collection("accounts").document(username).update("email", email);
    }
}