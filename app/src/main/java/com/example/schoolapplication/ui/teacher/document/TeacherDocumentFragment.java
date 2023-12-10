package com.example.schoolapplication.ui.teacher.document;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.schoolapplication.R;
import com.example.schoolapplication.ui.student.document.DownloadMaterialActivity;
import com.example.schoolapplication.ui.student.document.UploadAssignmentActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherDocumentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherDocumentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    Button uploadButton, downloadButton;
    public TeacherDocumentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherDocumentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherDocumentFragment newInstance(String param1, String param2) {
        TeacherDocumentFragment fragment = new TeacherDocumentFragment();
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
        view = inflater.inflate(R.layout.fragment_teacher_document, container, false);
        uploadButton = (Button) view.findViewById(R.id.buttonUploadTeacher);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UploadMaterialActivity.class);
                startActivity(intent);
            }
        });

        downloadButton = (Button) view.findViewById(R.id.buttonDownloadTeacher);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DownloadAssignmentActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
}