package com.example.acedata.ui;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.acedata.AppActivity;
import com.example.acedata.R;
import com.example.acedata.ui.datalist.DatalistFragment;

public class Pin_Fragment extends Fragment implements TextWatcher,View.OnKeyListener,View.OnFocusChangeListener {

    private EditText et_digit1, et_digit2, et_digit3, et_digit4;//In this et_digit1 is Most significant digit and et_digit4 is least significant digit
    private int whoHasFocus;
    char[] code = new char[4];//Store the digits in charArray.
    String pin = "";
    int sharedpin;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView= inflater.inflate(R.layout.fragment_pin_, container, false);

        initializeView(myView);
        et_digit1.requestFocus();//Left digit gets focus after adding of fragment in Container

        sharedPreferences=getActivity().getSharedPreferences("login_data", Context.MODE_PRIVATE);
        return myView;
    }
    private void initializeView(View view)
    {
        et_digit1 = (EditText) view.findViewById(R.id.first_Number);
        et_digit2 = (EditText) view.findViewById(R.id.second_Number);
        et_digit3 = (EditText) view.findViewById(R.id.third_Number);
        et_digit4 = (EditText) view.findViewById(R.id.fourth_Number);

        setListners();
    }
    private void setListners()
    {
        et_digit1.addTextChangedListener(this);
        et_digit2.addTextChangedListener(this);
        et_digit3.addTextChangedListener(this);
        et_digit4.addTextChangedListener(this);

        et_digit1.setOnKeyListener(this);
        et_digit2.setOnKeyListener(this);
        et_digit3.setOnKeyListener(this);
        et_digit4.setOnKeyListener(this);

        et_digit1.setOnFocusChangeListener(this);
        et_digit2.setOnFocusChangeListener(this);
        et_digit3.setOnFocusChangeListener(this);
        et_digit4.setOnFocusChangeListener(this);
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
                {   pin="";
                    pin+=et_digit1.getText().toString().charAt(0);
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
                        sharedpin=sharedPreferences.getInt("Pinvalue",-1);
                        if(sharedpin!=-1){
                            Log.d("pin",pin);
                            if(sharedpin==Integer.parseInt(pin.trim())){
                                pin="";
                                Intent intent=new Intent(getContext(), AppActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(getActivity(),"Pin doesn't match!",Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Toast.makeText(getActivity(),"Wrong Pin",Toast.LENGTH_SHORT).show();
                        }

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

                    default:
                        break;
                }
            }
        }
        return false;
    }

}