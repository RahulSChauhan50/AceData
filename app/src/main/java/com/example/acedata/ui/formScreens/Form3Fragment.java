package com.example.acedata.ui.formScreens;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.acedata.AppActivity;
import com.example.acedata.R;

import pl.droidsonroids.gif.GifImageView;

public class Form3Fragment extends Fragment {
    Button btn_submit,btn_back;
    GifImageView gif_layout;
    ScrollView background_scrollView;
    Handler handler;
    ConstraintLayout constraintLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView =  inflater.inflate(R.layout.fragment_form3,container,false);

        btn_submit = myView.findViewById(R.id.submit_form3);
        btn_back = myView.findViewById(R.id.back_to_form2);
        gif_layout = myView.findViewById(R.id.submit_gif);
        background_scrollView = myView.findViewById(R.id.scrollView2);
        constraintLayout = myView.findViewById(R.id.constraintLayout);

        return  myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gif_layout.setVisibility(View.VISIBLE);
                background_scrollView.setVisibility(View.INVISIBLE);
               // constraintLayout.setBackgroundColor(Color.parseColor("#000000"));
                constraintLayout.setBackgroundResource(R.drawable.side_nav_bar);
                handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {
                        ((AppActivity)getActivity()).Add_Datalist();

                    }
                };
                handler.postDelayed(r, 4000);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AppActivity)getActivity()).Add_Form2(view);
            }
        });
    }
}