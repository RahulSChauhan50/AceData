package com.example.acedata.ui;

import androidx.fragment.app.Fragment;
import android.os.Bundle;


import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.acedata.R;

import java.util.Timer;


public class Pin_Generator extends Fragment implements TextWatcher,View.OnKeyListener,View.OnFocusChangeListener {
    Button open_btn;
    Runnable runnable;
    View view;
    private EditText et_digit1, et_digit2, et_digit3, et_digit4;//In this et_digit1 is Most significant digit and et_digit4 is least significant digit
    private EditText et_digit5, et_digit6, et_digit7, et_digit8;
    private int whoHasFocus;
    char[] code = new char[4];//Store the digits in charArray.
    char[] code2 = new char[4];//Store the confirm pin digits in charArray.
    String pin = " ";
    String pin2 = " ";
    String temp;
    ProgressBar progressBar;
    int count=5000;
    Timer timer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_pin__generator, container, false);
        open_btn = myview.findViewById(R.id.open);
        progressBar= myview.findViewById(R.id.progressBar);
        timer = new Timer();
        open_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity)getActivity()).Add_Pin_Fragment(view);
            }
        });
        initializeView(myview);
        et_digit1.requestFocus();//Left digit gets focus after adding of fragment in Container
        return myview;
    }
    private void initializeView(View view)
    {
        et_digit1 = (EditText) view.findViewById(R.id.first_Number);
        et_digit2 = (EditText) view.findViewById(R.id.second_Number);
        et_digit3 = (EditText) view.findViewById(R.id.third_Number);
        et_digit4 = (EditText) view.findViewById(R.id.fourth_Number);
        et_digit5 = (EditText) view.findViewById(R.id.first_C_Number);
        et_digit6 = (EditText) view.findViewById(R.id.second_C_Number);
        et_digit7 = (EditText) view.findViewById(R.id.third_C_Number);
        et_digit8 = (EditText) view.findViewById(R.id.fourth_C_Number);
        setListners();
    }
    private void setListners()
    {
        et_digit1.addTextChangedListener(this);
        et_digit2.addTextChangedListener(this);
        et_digit3.addTextChangedListener(this);
        et_digit4.addTextChangedListener(this);
        et_digit5.addTextChangedListener(this);
        et_digit6.addTextChangedListener(this);
        et_digit7.addTextChangedListener(this);
        et_digit8.addTextChangedListener(this);

        et_digit1.setOnKeyListener(this);
        et_digit2.setOnKeyListener(this);
        et_digit3.setOnKeyListener(this);
        et_digit4.setOnKeyListener(this);
        et_digit5.setOnKeyListener(this);
        et_digit6.setOnKeyListener(this);
        et_digit7.setOnKeyListener(this);
        et_digit8.setOnKeyListener(this);

        et_digit1.setOnFocusChangeListener(this);
        et_digit2.setOnFocusChangeListener(this);
        et_digit3.setOnFocusChangeListener(this);
        et_digit4.setOnFocusChangeListener(this);
        et_digit5.setOnFocusChangeListener(this);
        et_digit6.setOnFocusChangeListener(this);
        et_digit7.setOnFocusChangeListener(this);
        et_digit8.setOnFocusChangeListener(this);
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus)
    {
        switch(v.getId())
        {
            case R.id.first_Number:
                whoHasFocus=1;
                break;

            case R.id.second_Number:
                whoHasFocus=2;
                break;

            case R.id.third_Number:
                whoHasFocus=3;
                break;

            case R.id.fourth_Number:
                whoHasFocus=4;
                break;

            case R.id.first_C_Number:
                whoHasFocus=5;
                break;

            case R.id.second_C_Number:
                whoHasFocus=6;
                break;

            case R.id.third_C_Number:
                whoHasFocus=7;
                break;

            case R.id.fourth_C_Number:
                whoHasFocus=8;
                break;

            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int    after)
    {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
    }
    @Override
    public void afterTextChanged(Editable s)
    {
        switch (whoHasFocus)
        {
            case 1:
                if(!et_digit1.getText().toString().isEmpty())
                {    pin+=et_digit1.getText().toString().charAt(0);
                    code[0]= et_digit1.getText().toString().charAt(0);
                    et_digit2.requestFocus();
                }
                break;

            case 2:
                if(!et_digit2.getText().toString().isEmpty())
                {    pin+=et_digit2.getText().toString().charAt(0);
                    code[1]= et_digit2.getText().toString().charAt(0);
                    et_digit3.requestFocus();
                }
                break;

            case 3:
                if(!et_digit3.getText().toString().isEmpty())
                {    pin+=et_digit3.getText().toString().charAt(0);
                    code[2]= et_digit3.getText().toString().charAt(0);
                    et_digit4.requestFocus();
                }
                break;

            case 4:
                if(!et_digit4.getText().toString().isEmpty())
                {   pin+=et_digit4.getText().toString().charAt(0);
                    code[3]= et_digit4.getText().toString().charAt(0);
                    if(pin.length()>=4){
                        Toast.makeText(getActivity(), pin, Toast.LENGTH_SHORT).show();
                        temp=pin;
                        pin=" ";
                    }


                }
            case 5:
                if(!et_digit5.getText().toString().isEmpty())
                {    pin2+=et_digit5.getText().toString().charAt(0);
                    code2[0]= et_digit5.getText().toString().charAt(0);
                    et_digit6.requestFocus();
                }
                break;

            case 6:
                if(!et_digit6.getText().toString().isEmpty())
                {    pin2+=et_digit6.getText().toString().charAt(0);
                    code2[1]= et_digit6.getText().toString().charAt(0);
                    et_digit7.requestFocus();
                }
                break;

            case 7:
                if(!et_digit7.getText().toString().isEmpty())
                {    pin2+=et_digit7.getText().toString().charAt(0);
                    code2[2]= et_digit7.getText().toString().charAt(0);
                    et_digit8.requestFocus();
                }
                break;

            case 8:
                if(!et_digit8.getText().toString().isEmpty())
                {   pin2+=et_digit8.getText().toString().charAt(0);
                    code2[3]= et_digit8.getText().toString().charAt(0);
                    if(pin2.length()>=4){
                        Toast.makeText(getActivity(), pin2, Toast.LENGTH_SHORT).show();
                        if(temp.equals(pin2)){
                            Toast.makeText(getActivity(), "Pin Generated SuccessFully", Toast.LENGTH_SHORT).show();
                            // Add Runnable and handler to start the next screen and set Progressbar
                            final Handler mHandler = new Handler();
                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.VISIBLE);
                                    count-=1000;
                                    if(count>0){
                                        mHandler.postDelayed(this,1000);
                                    }
                                    else{
                                        progressBar.setVisibility(View.INVISIBLE);
                                        //Call method of mainActivity by casting
                                       // ((MainActivity)getActivity()).Add_Pin_Fragment(view);
                                    }
                                }
                            };
                            mHandler.postDelayed(runnable,1000);
                        }
                        else{
                            Toast.makeText(getActivity(), "Entered Pin is not Matched", Toast.LENGTH_SHORT).show();
                        }
                        pin2=" ";
                    }


                }
                break;


            default:
                break;
        }
    }
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (keyCode == KeyEvent.KEYCODE_DEL)
            {
                switch(v.getId())
                {
                    case R.id.second_Number:
                        if (et_digit2.getText().toString().isEmpty())
                            et_digit1.requestFocus();
                        break;

                    case R.id.third_Number:
                        if (et_digit3.getText().toString().isEmpty())
                            et_digit2.requestFocus();
                        break;

                    case R.id.fourth_Number:
                        if (et_digit4.getText().toString().isEmpty())
                            et_digit3.requestFocus();
                        break;

                    case R.id.second_C_Number:
                        if (et_digit6.getText().toString().isEmpty())
                            et_digit5.requestFocus();
                        break;

                    case R.id.third_C_Number:
                        if (et_digit7.getText().toString().isEmpty())
                            et_digit6.requestFocus();
                        break;

                    case R.id.fourth_C_Number:
                        if (et_digit8.getText().toString().isEmpty())
                            et_digit7.requestFocus();
                        break;


                    default:
                        break;
                }
            }
        }
        return false;
    }

}