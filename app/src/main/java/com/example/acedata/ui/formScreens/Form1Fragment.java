package com.example.acedata.ui.formScreens;

import androidx.fragment.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.acedata.AppActivity;
import com.example.acedata.R;

public class Form1Fragment extends Fragment {
    FragmentManager fragmentManager;
    Button btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View form1 =  inflater.inflate(R.layout.fragment_form1,container,false);

        Bundle arguments = getArguments();
        String desired_string = arguments.getString("Hello");
        Log.d("value of hello",desired_string);

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
