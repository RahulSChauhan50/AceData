package com.example.acedata;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ProgressBar;

import com.example.acedata.network.RetrofitClientInstance;
import com.example.acedata.network.UploadReceiptService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AppActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        progressBar=findViewById(R.id.progressBar2);

        String Token = "Token 9dc7a0c191f9e74cbbd3fd15731a60bb23ee7073";

        UploadReceiptService service = RetrofitClientInstance.getRetrofitInstance().create(UploadReceiptService.class);

        Call<List<FormData>> call = service.getDataList(Token);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<FormData>>() {
            @Override
            public void onResponse(Call<List<FormData>> call, retrofit2.Response<List<FormData>> response) {
                List<FormData> posts = response.body();
                    progressBar.setVisibility(View.INVISIBLE);
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
                Log.d("Faied","Network request failed");
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}