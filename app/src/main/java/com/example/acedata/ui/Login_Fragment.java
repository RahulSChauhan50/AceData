package com.example.acedata.ui;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.acedata.R;

public class Login_Fragment extends Fragment {
    Button submit_btn;
    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_login_, container, false);
        submit_btn = myView.findViewById(R.id.submit);
        fragmentManager=getActivity().getSupportFragmentManager();
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction tr=fragmentManager.beginTransaction();
                tr.setReorderingAllowed(true);
                tr.replace(R.id.fragment_container_mainactivity,Pin_Generator.class,null);
                tr.commit();
            }
        });
    }
}