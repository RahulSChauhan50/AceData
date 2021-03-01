package com.example.acedata;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         fragmentManager = getFragmentManager();

//        Intent intent=new Intent(this,AppActivity.class);
//        startActivity(intent);
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
        FragmentTransaction trans= fragmentManager.beginTransaction();
        Login_Fragment login_fragment = new Login_Fragment();
        trans.replace(R.id.main_Frame,login_fragment);
        trans.commit();

    }
    public void Add_Login_Fragment(View view){
        FragmentTransaction trans= fragmentManager.beginTransaction();
        Login_Fragment login_fragment = new Login_Fragment();
        trans.replace(R.id.main_Frame,login_fragment);
        trans.commit();
    }
    public void Add_PinGenerator_Fragment(View view){
        FragmentTransaction trans= fragmentManager.beginTransaction();
        Pin_Generator pin_generator = new Pin_Generator();
        trans.replace(R.id.main_Frame,pin_generator);
        trans.commit();
    }
    public void Add_Pin_Fragment(View view){
        FragmentTransaction trans= fragmentManager.beginTransaction();
        Pin_Fragment pin = new Pin_Fragment();
        trans.replace(R.id.main_Frame,pin);
        trans.commit();
    }
}