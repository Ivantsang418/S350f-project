package com.example.schoolapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.schoolapplication.ui.teacher.academic.TeacherAcademicFragment;
import com.example.schoolapplication.ui.teacher.document.TeacherDocumentFragment;
import com.example.schoolapplication.ui.teacher.info.TeacherInfoFragment;
import com.example.schoolapplication.ui.teacher.notification.TeacherNotificationFragment;
import com.example.schoolapplication.ui.student.setting.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class TeacherActivity extends AppCompatActivity {
    BottomNavigationView navView;
    TeacherInfoFragment teacherInfoFragment = new TeacherInfoFragment();
    TeacherAcademicFragment teacherAcademicFragment = new TeacherAcademicFragment();
    TeacherDocumentFragment teacherDocumentFragment = new TeacherDocumentFragment();
    TeacherNotificationFragment notificationFragment = new TeacherNotificationFragment();

    SettingFragment settingFragment = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        navView = findViewById(R.id.nav_view_teacher);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, teacherInfoFragment).commit();
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_info:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, teacherInfoFragment).commit();
                        return true;
                    case R.id.navigation_academic:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,teacherAcademicFragment).commit();
                        return true;
                    case R.id.navigation_document:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,teacherDocumentFragment).commit();
                        return true;
                    case R.id.navigation_notifications:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,notificationFragment).commit();
                        return true;
                    case R.id.navigation_setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}