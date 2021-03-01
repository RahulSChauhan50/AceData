package com.example.acedata.ui;

import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.acedata.MainActivity;
import com.example.acedata.R;


public class Pin_Generator extends Fragment {
    Button open_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_pin__generator, container, false);
        open_btn = myview.findViewById(R.id.open);

        open_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).Add_Pin_Fragment(view);
            }
        });
        return myview;
    }
}