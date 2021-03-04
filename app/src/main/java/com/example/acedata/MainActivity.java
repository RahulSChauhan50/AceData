package com.example.acedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.acedata.ui.Login_Fragment;
import com.example.acedata.ui.Pin_Fragment;
import com.example.acedata.ui.Pin_Generator;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {
    Button btn;
    SharedPreferences sharedPreferences;
    String Token;
    final int ALL_PERMISSION=1;
    String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("login_data",MODE_PRIVATE);
        Token=sharedPreferences.getString("Tokenvalue",null);

        if(Token==null){
            if (savedInstanceState == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container_mainactivity, Login_Fragment.class, null)
                        .commit();
            }
        }
        else {
            if (savedInstanceState == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container_mainactivity, Pin_Fragment.class, null)
                        .commit();
            }
        }


       //askPermisson_function();


//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction trans= fragmentManager.beginTransaction();
//                Login_Fragment login_fragment = new Login_Fragment();
//                trans.replace(R.id.main_Frame,login_fragment);
//                trans.commit();
//                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
//            }
//        });
//        FragmentTransaction trans= fragmentManager.beginTransaction();
//        Login_Fragment login_fragment = new Login_Fragment();
//        trans.replace(R.id.main_Frame,login_fragment);
//        trans.commit();

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        if(requestCode==ALL_PERMISSION){
//            Log.d("permission",String.valueOf(grantResults[0])+String.valueOf(grantResults[1])+String.valueOf(grantResults[2])+String.valueOf(grantResults[3]));
//            if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED
//            || grantResults[2] != PackageManager.PERMISSION_GRANTED || grantResults[3] != PackageManager.PERMISSION_GRANTED) {
//
//                new MaterialAlertDialogBuilder(MainActivity.this)
//                        .setTitle("Permission Required")
//                        .setMessage("App needs to access CAMERA, GPS and STORAGE permissions in order to work properly.")
//                        .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                askPermission_function();
//                            }
//                        })
//                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Toast.makeText(MainActivity.this,"Permission Not Granted",Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .show();
//            }
//        }
//    }
//    public void Add_Login_Fragment(View view){
//        FragmentTransaction trans= fragmentManager.beginTransaction();
//        Login_Fragment login_fragment = new Login_Fragment();
//        trans.replace(R.id.main_Frame,login_fragment);
//        trans.commit();
//    }
//    public void Add_PinGenerator_Fragment(View view){
//        FragmentTransaction trans= fragmentManager.beginTransaction();
//        Pin_Generator pin_generator = new Pin_Generator();
//        trans.replace(R.id.main_Frame,pin_generator);
//        trans.commit();
//    }
//    public void Add_Pin_Fragment(View view){
//        FragmentTransaction trans= fragmentManager.beginTransaction();
//        Pin_Fragment pin = new Pin_Fragment();
//        trans.replace(R.id.main_Frame,pin);
//        trans.commit();
//    }
//    public void askPermission_function(){
//
//        for(String permission:permissions){
//            if(ActivityCompat.checkSelfPermission(MainActivity.this,permission)!= PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSION);
//                break;
//            }
//        }
//
//    }
}