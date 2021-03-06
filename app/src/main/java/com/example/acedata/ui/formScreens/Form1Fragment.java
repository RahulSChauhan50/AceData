package com.example.acedata.ui.formScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.acedata.AppActivity;
import com.example.acedata.FormData;
import com.example.acedata.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

public class Form1Fragment extends Fragment {

    EditText editText_adhar,editText_name,editText_phone,editText_address;
    Button btn;
    String name_value,address_value,phone_value,adhar_value;
    FormData obj;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View form1 =  inflater.inflate(R.layout.fragment_form1,container,false);

       editText_name=form1.findViewById(R.id.TextInputEditTextName);
       editText_address=form1.findViewById(R.id.TextInputEditTextAddress);
       editText_adhar=form1.findViewById(R.id.TextInputEditTextAdhar);
       editText_phone=form1.findViewById(R.id.TextInputEditTextPhone);
        btn=form1.findViewById(R.id.save);

       return form1;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String desired_string = arguments.getString("form1_pass");

        //converting string data back to object
        Gson gson = new Gson();
        obj = gson.fromJson(desired_string, FormData.class);
        Log.d("value of object",obj.getAddress()+obj.getAdhar()+obj.getMobile_no()+obj.getName());

        setValues(obj);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting values from editText
                name_value=editText_name.getText().toString().trim();
                address_value=editText_address.getText().toString().trim();
                phone_value=editText_phone.getText().toString().trim();
                adhar_value=editText_adhar.getText().toString().trim();

                //checking for empty editText
                if(name_value.equals("") || phone_value.equals("") || adhar_value.equals("") || address_value.equals("")){
                    Toast.makeText(getContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else {
                    //changing object value to new values
                    obj.setAddress(address_value);
                    obj.setMobile_no(phone_value);
                    obj.setName(name_value);
                    obj.setAdhar(adhar_value);

                    ///sending data to form2Fragment
                    Gson gson = new Gson();
                    String object_pass = gson.toJson(obj);

                    Form2Fragment form2Fragment=new Form2Fragment();
                    Bundle arguments = new Bundle();
                    arguments.putString( "form2_pass" , object_pass);
                    form2Fragment.setArguments(arguments);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container_appactivity, form2Fragment,null)
                            .commit();

                }

            }
        });

    }

    public void setValues(FormData obj){
        editText_name.setText(obj.getName());
        editText_phone.setText(obj.getMobile_no());
        editText_address.setText(obj.getAddress());
        editText_adhar.setText(obj.getAdhar());
    }
}
