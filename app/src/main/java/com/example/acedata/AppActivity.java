package com.example.acedata;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ProgressBar;

import com.example.acedata.ui.formScreens.Form1Fragment;
import com.example.acedata.ui.formScreens.Form2Fragment;
import com.example.acedata.ui.formScreens.Form3Fragment;
import com.example.acedata.network.RetrofitClientInstance;
import com.example.acedata.network.UploadReceiptService;
import com.example.acedata.ui.datalist.DatalistFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AppActivity extends AppCompatActivity {
     FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        fragmentManager = getFragmentManager();
        FragmentTransaction transact = fragmentManager.beginTransaction();
        Form1Fragment form1 = new Form1Fragment();
        transact.replace(R.id.App_activity,form1);
        transact.commit();
    }
    public void Add_Form1(View view){
        FragmentTransaction transact = fragmentManager.beginTransaction();
        Form1Fragment form1 = new Form1Fragment();
        transact.replace(R.id.App_activity,form1);
        transact.commit();
    }
    public void Add_Form2(View view){
        FragmentTransaction transact = fragmentManager.beginTransaction();
        Form2Fragment form2 = new Form2Fragment();
        transact.replace(R.id.App_activity,form2);
        transact.commit();
    }
    public void Add_Form3(View view){
        FragmentTransaction transact = fragmentManager.beginTransaction();
        Form3Fragment form3 = new Form3Fragment();
        transact.replace(R.id.App_activity,form3);
        transact.commit();
    }
}