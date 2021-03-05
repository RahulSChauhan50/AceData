package com.example.acedata;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.acedata.ui.datalist.DatalistFragment;
import com.example.acedata.ui.formScreens.Form1Fragment;
import com.example.acedata.ui.formScreens.Form2Fragment;
import com.example.acedata.ui.formScreens.Form3Fragment;

public class AppActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        sharedPreferences=getSharedPreferences("login_data",MODE_PRIVATE);

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_logout: {

                SharedPreferences.Editor ed=sharedPreferences.edit();
                ed.remove("Tokenvalue");
                ed.remove("Pinvalue");
                ed.apply();

                Intent intent = new Intent(AppActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }
}