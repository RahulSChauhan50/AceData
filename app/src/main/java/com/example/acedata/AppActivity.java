package com.example.acedata;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.acedata.network.RetrofitClientInstance;
import com.example.acedata.network.UploadReceiptService;
import com.example.acedata.ui.datalist.DatalistFragment;
import com.example.acedata.ui.formScreens.Form1Fragment;
import com.example.acedata.ui.formScreens.Form2Fragment;
import com.example.acedata.ui.formScreens.Form3Fragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AppActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    ConstraintLayout parentView_appActivity;
    SharedPreferences storeObjectShared;
    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;
    NotificationChannel upload_notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        parentView_appActivity=findViewById(R.id.activity_app_parentView);

        sharedPreferences=getSharedPreferences("login_data",MODE_PRIVATE);

        storeObjectShared=getSharedPreferences("Stored_objects", Context.MODE_PRIVATE);

        //https://developer.android.com/training/notify-user/channels
        //https://stuff.mit.edu/afs/sipb/project/android/docs/training/notify-user/display-progress.html
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            upload_notification=new NotificationChannel("upload_channel","UPLOAD NOTIFICATION",NotificationManager.IMPORTANCE_DEFAULT);
            upload_notification.setLightColor(Color.GREEN);
            mNotifyManager.createNotificationChannel(upload_notification);
        }

        mBuilder = new NotificationCompat.Builder(AppActivity.this,"upload_channel");
        mBuilder.setContentTitle("File Upload")
                .setContentText("Upload in progress")
                //.setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_baseline_upload_file_24);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_appactivity, DatalistFragment.class, null)
                    .commit();
        }
    }

    public void Add_Form1(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_appactivity, Form1Fragment.class, null)
                .commit();
    }

    public void Add_Form2(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_appactivity, Form2Fragment.class, null)
                .commit();
    }

    public void Add_Form3(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_appactivity, Form3Fragment.class, null)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionbar_logout: {

                SharedPreferences.Editor ed=sharedPreferences.edit();
                ed.remove("Tokenvalue");
                ed.remove("Pinvalue");
                ed.apply();

                Intent intent = new Intent(AppActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }

    @Override
    public void onBackPressed() {
        Fragment currentfragment=getSupportFragmentManager().findFragmentById(R.id.fragment_container_appactivity);
        if(currentfragment instanceof DatalistFragment){
            //Log.d("current fragment","recyclerview");
            super.onBackPressed();
        }
        else{
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_appactivity, DatalistFragment.class, null)
                        .commit();
        }
    }

    public void uploadData_function(FormData passesObj){
        uploadThread thread=new uploadThread(passesObj);
        thread.start();
    }

    public class uploadThread extends Thread{

        FormData obj;

        public uploadThread(FormData objPassed) {
            this.obj=objPassed;
        }

        @Override
        public void run() {
            if(obj.getImage1Uri()==null || obj.getImage2Uri()==null || obj.getImage3Uri()==null || obj.getImage4Uri()==null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AppActivity.this,"Please select all the images",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{

                UploadReceiptService service = RetrofitClientInstance.getRetrofitInstance().create(UploadReceiptService.class);

                File image1File = new File(obj.getImage1Uri());
                File image2File = new File(obj.getImage2Uri());
                File image3File = new File(obj.getImage3Uri());
                File image4File = new File(obj.getImage4Uri());

                RequestBody requestFile1 =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"),
                                image1File
                        );
                RequestBody requestFile2 =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"),
                                image1File
                        );
                RequestBody requestFile3 =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"),
                                image1File
                        );
                RequestBody requestFile4 =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"),
                                image1File
                        );


                MultipartBody.Part avatar1 =
                        MultipartBody.Part.createFormData("avatar1", image1File.getName(), requestFile1);
                MultipartBody.Part avatar2 =
                        MultipartBody.Part.createFormData("avatar2", image2File.getName(), requestFile2);
                MultipartBody.Part avatar3 =
                        MultipartBody.Part.createFormData("avatar3", image3File.getName(), requestFile3);
                MultipartBody.Part avatar4 =
                        MultipartBody.Part.createFormData("avatar4", image4File.getName(), requestFile4);


                String Token ="Token "+sharedPreferences.getString("Tokenvalue",null);
                String url="https://baseloan.herokuapp.com/owner/"+obj.getAdhar()+"/";
                RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), obj.getName());
                RequestBody mobile_no = RequestBody.create(MediaType.parse("multipart/form-data"), obj.getMobile_no());
                RequestBody Address = RequestBody.create(MediaType.parse("multipart/form-data"), obj.getAddress());
                RequestBody adhar = RequestBody.create(MediaType.parse("multipart/form-data"), obj.getAdhar());


                Call<FormData> call = service.putData(Token,avatar1,avatar2,avatar3,avatar4, name, mobile_no,Address,adhar,url);

                /////calling notification////
                mBuilder
                        .setContentText("Upload in progress")
                        .setProgress(0, 0, true);
                // Displays the progress bar for the first time.
                mNotifyManager.notify(1, mBuilder.build());


                call.enqueue(new Callback<FormData>() {
                    @Override
                    public void onResponse(Call<FormData> call, retrofit2.Response<FormData> response) {

                        if(response.code()==200){
                           // Log.d("Upload Successfull",response.message()+" "+String.valueOf(response.code()));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(parentView_appActivity,"Successfully Uploaded", Snackbar.LENGTH_SHORT).show();
                                }
                            });


                            SharedPreferences.Editor ed=storeObjectShared.edit();
                            ed.remove(obj.getAdhar());
                            ed.apply();

                            ///stopping notification////
                            mBuilder.setContentText("Upload complete")
                                    // Removes the progress bar
                                    .setProgress(0,0,false);
                            mNotifyManager.notify(1, mBuilder.build());


                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(parentView_appActivity,"Error uploading data!", Snackbar.LENGTH_SHORT).show();
                                }
                            });

                            Gson gson = new Gson();
                            String object_pass = gson.toJson(obj);

                            SharedPreferences.Editor ed=storeObjectShared.edit();
                            ed.putString(obj.getAdhar(),object_pass);
                            ed.commit();

                           // Log.d("Upload error",response.message()+" "+String.valueOf(response.code()));

                            ///stopping notification////
                            mBuilder.setContentText("Upload error")
                                    // Removes the progress bar
                                    .setProgress(0,0,false);
                            mNotifyManager.notify(1, mBuilder.build());
                        }

                    }

                    @Override
                    public void onFailure(Call<FormData> call, Throwable t) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(parentView_appActivity,"Network error!", Snackbar.LENGTH_SHORT).show();
                            }
                        });

                        Gson gson = new Gson();
                        String object_pass = gson.toJson(obj);

                        SharedPreferences.Editor ed=storeObjectShared.edit();
                        ed.putString(obj.getAdhar(),object_pass);
                        ed.commit();

                       // Log.d("error",t.getMessage());

                        ///stopping notification////
                        mBuilder.setContentText("Upload error")
                                // Removes the progress bar
                                .setProgress(0,0,false);
                        mNotifyManager.notify(1, mBuilder.build());
                    }
                });
            }
        }
    }

}