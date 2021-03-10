package com.example.acedata.ui.formScreens;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.acedata.AppActivity;
import com.example.acedata.R;

import pl.droidsonroids.gif.GifImageView;

public class Form3Fragment extends Fragment {
    Button btn_submit;
    GifImageView gif;
    Handler handler;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView =  inflater.inflate(R.layout.fragment_form3,container,false);

        btn_submit = myView.findViewById(R.id.submit);
        gif = myView.findViewById(R.id.submit_gif);
        return  myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gif.setVisibility(View.VISIBLE);

                handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {
                        ((AppActivity)getActivity()).Add_Datalist(view);

                    }
                };
                handler.postDelayed(r, 1000);
            }
        });
    }
}