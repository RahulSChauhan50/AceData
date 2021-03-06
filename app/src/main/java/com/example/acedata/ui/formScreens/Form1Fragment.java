package com.example.acedata.ui.formScreens;

import androidx.fragment.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.acedata.AppActivity;
import com.example.acedata.FormData;
import com.example.acedata.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

public class Form1Fragment extends Fragment {
    FragmentManager fragmentManager;
    EditText editText_adhar,editText_name,editText_phone,editText_address;
    Button btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View form1 =  inflater.inflate(R.layout.fragment_form1,container,false);

       editText_name=form1.findViewById(R.id.TextInputEditTextName);
       editText_address=form1.findViewById(R.id.TextInputEditTextAddress);
       editText_adhar=form1.findViewById(R.id.TextInputEditTextAdhar);
       editText_phone=form1.findViewById(R.id.TextInputEditTextPhone);

        Bundle arguments = getArguments();
        String desired_string = arguments.getString("itemstring");

        //converting string data back to object
        Gson gson = new Gson();
        FormData obj = gson.fromJson(desired_string, FormData.class);
        Log.d("value of object",obj.getAddress()+obj.getAdhar()+obj.getMobile_no()+obj.getName());

        setValues(obj);

       btn=form1.findViewById(R.id.save);
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ((AppActivity)getActivity()).Add_Form2(view);
           }
       });
       return form1;
    }

    public void setValues(FormData obj){
        editText_name.setText(obj.getName());
        editText_phone.setText(obj.getMobile_no());
        editText_address.setText(obj.getAddress());
        editText_adhar.setText(obj.getAdhar());
    }
}
