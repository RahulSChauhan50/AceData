package com.example.acedata.ui.formScreens;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.acedata.AppActivity;
import com.example.acedata.R;

public class Form2Fragment extends Fragment {
    Button btn_back,btn_next;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View form3= inflater.inflate(R.layout.fragment_form2,container,false);

        btn_next = form3.findViewById(R.id.open);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppActivity)getActivity()).Add_Form3(view);
            }
        });

        return  form3;
    }
}
