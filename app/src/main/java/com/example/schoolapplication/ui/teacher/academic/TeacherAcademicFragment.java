package com.example.schoolapplication.ui.teacher.academic;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolapplication.MainActivity;
import com.example.schoolapplication.R;
import com.example.schoolapplication.RecyclerViewInterface;
import com.example.schoolapplication.StudentInfoAdapter;
import com.example.schoolapplication.StudentInfoItem;
import com.example.schoolapplication.ui.teacher.info.TeacherInfoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherAcademicFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherAcademicFragment extends Fragment implements RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    List<StudentInfoItem> items;
    public TeacherAcademicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherAcademicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherAcademicFragment newInstance(String param1, String param2) {
        TeacherAcademicFragment fragment = new TeacherAcademicFragment();
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
        view = inflater.inflate(R.layout.fragment_teacher_academic, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView recyclerView = view.findViewById(R.id.studentAcademicRecyclerView);
        items = new ArrayList<StudentInfoItem>();
        readData(new FirestoreCallback() {
            @Override
            public void onCallback(List<StudentInfoItem> list) {
                setRecyclerView(recyclerView, list);
            }
        });
    }

    private void readData(FirestoreCallback firestoreCallback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("accounts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getData().get("usertype").equals("student")) {
                                    String name = document.getData().get("firstName").toString() + " " + document.getData().get("lastName").toString();
                                    items.add(new StudentInfoItem(document.getId(), name, document.getData().get("studentId").toString(), document.getData().get("phone").toString(), document.getData().get("email").toString()));
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            }
                            firestoreCallback.onCallback(items);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "Item click position: " + items.get(position).getUsername());
        Intent intent = new Intent(getActivity(), EditAcademicActivity.class);
        intent.putExtra("USERNAME", items.get(position).getUsername());
        startActivity(intent);
    }

    private interface FirestoreCallback {
        void onCallback(List<StudentInfoItem> list);
    }

    private void setRecyclerView(RecyclerView recyclerView, List<StudentInfoItem> items) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new StudentInfoAdapter(requireActivity().getApplicationContext(), items, this));
    }
}