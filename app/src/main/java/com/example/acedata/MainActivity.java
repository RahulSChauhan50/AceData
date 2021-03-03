package com.example.acedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.acedata.ui.Login_Fragment;
import com.example.acedata.ui.Pin_Fragment;
import com.example.acedata.ui.Pin_Generator;

public class MainActivity extends AppCompatActivity {
    Button btn;
    FragmentManager fragmentManager;
    final int ALL_PERMISSION=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // askPermisson_function();

         fragmentManager = getFragmentManager();

        Intent intent=new Intent(this,AppActivity.class);
        startActivity(intent);
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
//    public void askPermisson_function(){
//        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE,
//        Manifest.permission.READ_EXTERNAL_STORAGE,
//        Manifest.permission.ACCESS_COARSE_LOCATION,
//        Manifest.permission.CAMERA};
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