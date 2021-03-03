package com.example.acedata.ui.formScreens;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.acedata.AppActivity;
import com.example.acedata.R;

public class Form1Fragment extends Fragment {
    Button btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View form1 =  inflater.inflate(R.layout.fragment_form1,container,false);
       btn=form1.findViewById(R.id.save);
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ((AppActivity)getActivity()).Add_Form2(view);
           }
       });
       return form1;
    }
}
