package com.example.acedata.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.acedata.MainActivity;
import com.example.acedata.R;

public class Login_Fragment extends Fragment {
    FragmentManager fragmentManager;
    Button submit_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_login_, container, false);

        submit_btn = myView.findViewById(R.id.submit);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).Add_PinGenerator_Fragment(view);
            }
        });
       return myView;
    }
}