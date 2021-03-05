package com.example.acedata;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.acedata.ui.datalist.DatalistFragment;
import com.example.acedata.ui.formScreens.Form1Fragment;
import com.example.acedata.ui.formScreens.Form2Fragment;
import com.example.acedata.ui.formScreens.Form3Fragment;

public class AppActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_appactivity, DatalistFragment.class, null)
                    .commit();
        }
    }

    public void Add_Form1(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_appactivity, Form1Fragment.class, null)
                .commit();
    }

    public void Add_Form2(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_appactivity, Form2Fragment.class, null)
                .commit();
    }

    public void Add_Form3(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_appactivity, Form3Fragment.class, null)
                .commit();
    }
}