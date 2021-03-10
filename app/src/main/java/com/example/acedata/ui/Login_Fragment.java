package com.example.acedata.ui;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.acedata.FormData;
import com.example.acedata.R;
import com.example.acedata.network.RetrofitClientInstance;
import com.example.acedata.network.UploadReceiptService;

import retrofit2.Call;
import retrofit2.Callback;

public class Login_Fragment extends Fragment {
    ProgressBar progressBar;
    Button submit_btn;
    FragmentManager fragmentManager;
    String username;
    String password;
    EditText edittextusername;
    EditText edittextpassword;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_login_, container, false);
        submit_btn = myView.findViewById(R.id.submit);
        edittextusername=myView.findViewById(R.id.textinputedittext_username);
        edittextpassword=myView.findViewById(R.id.textinputedittext_password);
        progressBar=myView.findViewById(R.id.progressBar_signin);
        sharedPreferences=getActivity().getSharedPreferences("login_data", Context.MODE_PRIVATE);
        fragmentManager=getActivity().getSupportFragmentManager();
        return myView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=edittextusername.getText().toString().trim();
                password=edittextpassword.getText().toString().trim();

                if(username=="" || password==""){
                    Toast.makeText(getActivity(),"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);

                    UploadReceiptService service = RetrofitClientInstance.getRetrofitInstance().create(UploadReceiptService.class);

                    Call<FormData> call = service.signin(username,password);

                    call.enqueue(new Callback<FormData>() {
                        @Override
                        public void onResponse(Call<FormData> call, retrofit2.Response<FormData> response) {
                            progressBar.setVisibility(View.GONE);

                            if(response.code()==200){
                                SharedPreferences.Editor ed=sharedPreferences.edit();
                                ed.putString("Tokenvalue",response.body().getKey());
                                ed.commit();

                                FragmentTransaction tr=fragmentManager.beginTransaction();
                                tr.setReorderingAllowed(true);
                                tr.replace(R.id.fragment_container_mainactivity,Pin_Generator.class,null);
                                tr.commit();

                                //Log.d("key",response.body().getKey());
                            }
                            else{
                                Toast.makeText(getActivity(),"Invalid credentials",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<FormData> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            //Log.d("Faied","SignIn failed "+t.getMessage());
                            Toast.makeText(getActivity(),"SignIn Failed....",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}