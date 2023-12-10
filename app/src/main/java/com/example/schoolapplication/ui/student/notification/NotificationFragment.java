package com.example.schoolapplication.ui.student.notification;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schoolapplication.NotificationAdapter;
import com.example.schoolapplication.NotificationItem;
import com.example.schoolapplication.R;
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
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    List<NotificationItem> items;
    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        RecyclerView recyclerView = view.findViewById(R.id.studentNotificationRecyclerView);
        items = new ArrayList<NotificationItem>();
        readData(new FirestoreCallback() {
            @Override
            public void onCallback(List<NotificationItem> list) {
                setRecyclerView(recyclerView, list);
            }
        });
    }

    private void readData(FirestoreCallback firestoreCallback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notifications")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                    items.add(new NotificationItem(document.getData().get("title").toString(), document.getData().get("message").toString()));
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            firestoreCallback.onCallback(items);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    private interface FirestoreCallback {
        void onCallback(List<NotificationItem> list);
    }

    private void setRecyclerView(RecyclerView recyclerView, List<NotificationItem> items) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new NotificationAdapter(requireActivity().getApplicationContext(), items, null));
    }

}