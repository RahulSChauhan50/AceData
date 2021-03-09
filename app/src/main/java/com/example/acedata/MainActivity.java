package com.example.acedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.acedata.ui.Login_Fragment;
import com.example.acedata.ui.Pin_Fragment;
import com.example.acedata.ui.Pin_Generator;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    String Token;
    int pin;
    final int ALL_PERMISSION = 1;
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        askPermission_function();

        sharedPreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        Token = sharedPreferences.getString("Tokenvalue", null);
        pin=sharedPreferences.getInt("Pinvalue",-1);

        if (Token == null || pin==-1) {
            if (savedInstanceState == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container_mainactivity, Login_Fragment.class, null)
                        .commit();
            }
        } else {
            if (savedInstanceState == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container_mainactivity, Pin_Fragment.class, null)
                        .commit();
            }
        }

    }

        @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == ALL_PERMISSION) {
                //Log.d("permission", String.valueOf(grantResults[0]) + String.valueOf(grantResults[1]) + String.valueOf(grantResults[2]) + String.valueOf(grantResults[3]));
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED
                        || grantResults[2] != PackageManager.PERMISSION_GRANTED || grantResults[3] != PackageManager.PERMISSION_GRANTED) {

                    new MaterialAlertDialogBuilder(MainActivity.this)
                            .setTitle("Permission Required")
                            .setMessage("App needs to access CAMERA, GPS and STORAGE permissions in order to work properly.")
                            .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    askPermission_function();
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(MainActivity.this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
            }
        }
    public void Add_Login_Fragment(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_mainactivity, Login_Fragment.class, null)
                .commit();
    }

    public void Add_PinGenerator_Fragment(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_mainactivity, Pin_Generator.class, null)
                .commit();
    }

    public void Add_Pin_Fragment(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_mainactivity, Pin_Fragment.class, null)
                .commit();
    }
    public void askPermission_function(){

        for(String permission:permissions){
            if(ActivityCompat.checkSelfPermission(MainActivity.this,permission)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSION);
                break;
            }
        }

    }
}