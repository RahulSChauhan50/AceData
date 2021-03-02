package com.example.acedata.ui.datalist;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.acedata.FormData;
import com.example.acedata.R;
import com.example.acedata.network.RetrofitClientInstance;
import com.example.acedata.network.UploadReceiptService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


public class DatalistFragment extends Fragment {

    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView=inflater.inflate(R.layout.fragment_datalist,container,false);
        progressBar=fragmentView.findViewById(R.id.progressBar2);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        String Token = "Token 9dc7a0c191f9e74cbbd3fd15731a60bb23ee7073";

        UploadReceiptService service = RetrofitClientInstance.getRetrofitInstance().create(UploadReceiptService.class);

        Call<List<FormData>> call = service.getDataList(Token);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<FormData>>() {
            @Override
            public void onResponse(Call<List<FormData>> call, retrofit2.Response<List<FormData>> response) {
                List<FormData> posts = response.body();
                progressBar.setVisibility(View.GONE);
                for ( FormData post : posts) {
                    String content = "";
                    content += "name: " + post.getName() + "\n";
                    content += "Address: " + post.getAddress() + "\n";
                    content += "adhar: " + post.getAdhar() + "\n";
                    content += "mobile: " + post.getMobile_no() + "\n\n";

                    Log.d("Content",content);

                }
            }

            @Override
            public void onFailure(Call<List<FormData>> call, Throwable t) {
                Log.d("Faied","Network request failed "+t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
