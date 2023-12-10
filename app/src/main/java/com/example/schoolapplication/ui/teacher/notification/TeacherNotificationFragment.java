package com.example.schoolapplication.ui.teacher.notification;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.schoolapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherNotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherNotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText titleView, messageView;
    Button createButton;
    View view;
    Snackbar mySnackbar;

    public TeacherNotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherNotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherNotificationFragment newInstance(String param1, String param2) {
        TeacherNotificationFragment fragment = new TeacherNotificationFragment();
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
        view = inflater.inflate(R.layout.fragment_teacher_notification, container, false);

        titleView = (EditText) view.findViewById(R.id.editTextTitle);
        messageView = (EditText) view.findViewById(R.id.editTextMessage);
        createButton = (Button) view.findViewById(R.id.buttonCreateNotification);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!titleView.getText().toString().equals("") && !messageView.getText().toString().equals("") ) {
                    Map<String, Object> newMessage = new HashMap<>();
                    newMessage.put("title", titleView.getText().toString());
                    newMessage.put("message", messageView.getText().toString());
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("notifications")
                            .add(newMessage)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                    titleView.setText("");
                                    messageView.setText("");
                                    mySnackbar = Snackbar.make(view, "Create notification success", Snackbar.LENGTH_LONG);
                                    mySnackbar.show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }

            }
        });

        return view;
    }
}