package com.example.schoolapplication;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.schoolapplication.ui.student.academic.AcademicFragment;
import com.example.schoolapplication.ui.student.document.DocumentFragment;
import com.example.schoolapplication.ui.student.info.InfoFragment;
import com.example.schoolapplication.ui.student.notification.NotificationFragment;
import com.example.schoolapplication.ui.student.setting.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.schoolapplication.databinding.ActivityStudentBinding;
import com.google.android.material.navigation.NavigationBarView;

public class StudentActivity extends AppCompatActivity {
    BottomNavigationView navView;
    InfoFragment infoFragment = new InfoFragment();
    AcademicFragment academicFragment = new AcademicFragment();
    DocumentFragment documentFragment = new DocumentFragment();
    NotificationFragment notificationFragment = new NotificationFragment();

    SettingFragment settingFragment = new SettingFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        navView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,infoFragment).commit();
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_info:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,infoFragment).commit();
                        return true;
                    case R.id.navigation_academic:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,academicFragment).commit();
                        return true;
                    case R.id.navigation_document:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,documentFragment).commit();
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
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_info, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_student);
//        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}