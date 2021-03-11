package com.example.acedata;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.acedata.location.FetchAddressTask;
import com.example.acedata.network.RetrofitClientInstance;
import com.example.acedata.network.UploadReceiptService;
import com.example.acedata.ui.datalist.DatalistFragment;
import com.example.acedata.ui.formScreens.Form1Fragment;
import com.example.acedata.ui.formScreens.Form2Fragment;
import com.example.acedata.ui.formScreens.Form3Fragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AppActivity extends AppCompatActivity implements
        FetchAddressTask.OnTaskCompleted {

    SharedPreferences sharedPreferences;
    ConstraintLayout parentView_appActivity;
    SharedPreferences storeObjectShared;
    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;
    NotificationChannel upload_notification;

    Location mLastLocation;
    public String addressLocation = null;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        parentView_appActivity = findViewById(R.id.activity_app_parentView);

        sharedPreferences = getSharedPreferences("login_data", MODE_PRIVATE);

        storeObjectShared = getSharedPreferences("Stored_objects", Context.MODE_PRIVATE);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(AppActivity.this);
        getLocation_function();

        //https://developer.android.com/training/notify-user/channels
        //https://stuff.mit.edu/afs/sipb/project/android/docs/training/notify-user/display-progress.html
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            upload_notification = new NotificationChannel("upload_channel", "UPLOAD NOTIFICATION", NotificationManager.IMPORTANCE_DEFAULT);
            upload_notification.setLightColor(Color.GREEN);
            mNotifyManager.createNotificationChannel(upload_notification);
        }

        mBuilder = new NotificationCompat.Builder(AppActivity.this, "upload_channel");
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

    public void Add_Datalist(View view) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_appactivity, DatalistFragment.class, null)
                .commit();

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

                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.remove("Tokenvalue");
                ed.remove("Pinvalue");
                ed.apply();

                Intent intent = new Intent(AppActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.get_my_location:{
                getLocation_function();
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }

    }

    @Override
    public void onBackPressed() {
        Fragment currentfragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container_appactivity);
        if (currentfragment instanceof DatalistFragment) {
            //Log.d("current fragment","recyclerview");
            super.onBackPressed();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_appactivity, DatalistFragment.class, null)
                    .commit();
        }
    }

    @SuppressLint("MissingPermission")
    void getLocation_function() {

        Toast.makeText(AppActivity.this,"Fetching Location...",Toast.LENGTH_LONG).show();
        CancellationTokenSource cts = new CancellationTokenSource();
        mFusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cts.getToken())
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Log.d("coordinates", String.valueOf(location.getLongitude()) + " " + String.valueOf(location.getLatitude()));
                            Toast.makeText(AppActivity.this, "Location Fetched Successfully", Toast.LENGTH_SHORT).show();
                            mLastLocation = location;
                            new FetchAddressTask(AppActivity.this,
                                    AppActivity.this).execute(location);
                        } else {
                            // Log.d("location", "location is null");
                            Toast.makeText(AppActivity.this, "Failed to fetch location \nCheck if GPS is turned on", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Log.d("failed", e.toString());
                        Toast.makeText(AppActivity.this, "Failed to fetch location \nCheck if GPS is turned on", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onTaskCompleted(String result) {
        //Log.d("current address", result);
        // Log.d("imagenumber", String.valueOf(currentImageNumber)+" "+currentPhotoPath);

        addressLocation = result;
    }

    public void uploadData_function(FormData passesObj, int imageNumber) {

        new Thread(new uploadThread(passesObj, imageNumber)).start();
    }

    public class uploadThread implements Runnable {
        FormData obj;
        int imageNumber;

        public uploadThread(FormData objPassed, int imageNumberPassed) {
            this.obj = objPassed;
            this.imageNumber = imageNumberPassed;
        }

        @Override
        public void run() {

            UploadReceiptService service = RetrofitClientInstance.getRetrofitInstance().create(UploadReceiptService.class);

            File imageFile;
            MultipartBody.Part avatar = null;
            switch (imageNumber) {
                case 0: {
                    try {
                        imageFile = new File(obj.getImage1Uri());

                        RequestBody requestFile =
                                RequestBody.create(
                                        MediaType.parse("multipart/form-data"),
                                        imageFile
                                );

                        avatar = MultipartBody.Part.createFormData("avatar1", imageFile.getName(), requestFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 1: {
                    try {
                        imageFile = new File(obj.getImage2Uri());

                        RequestBody requestFile =
                                RequestBody.create(
                                        MediaType.parse("multipart/form-data"),
                                        imageFile
                                );

                        avatar = MultipartBody.Part.createFormData("avatar2", imageFile.getName(), requestFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 2: {
                    try {
                        imageFile = new File(obj.getImage3Uri());

                        RequestBody requestFile =
                                RequestBody.create(
                                        MediaType.parse("multipart/form-data"),
                                        imageFile
                                );

                        avatar = MultipartBody.Part.createFormData("avatar3", imageFile.getName(), requestFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 3: {
                    try {
                        imageFile = new File(obj.getImage4Uri());

                        RequestBody requestFile =
                                RequestBody.create(
                                        MediaType.parse("multipart/form-data"),
                                        imageFile
                                );

                        avatar = MultipartBody.Part.createFormData("avatar4", imageFile.getName(), requestFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


            String Token = "Token " + sharedPreferences.getString("Tokenvalue", null);
            String url = "https://baseloan.herokuapp.com/owner/" + obj.getAdhar() + "/";
            RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), obj.getName());
            RequestBody mobile_no = RequestBody.create(MediaType.parse("multipart/form-data"), obj.getMobile_no());
            RequestBody Address = RequestBody.create(MediaType.parse("multipart/form-data"), obj.getAddress());
            RequestBody adhar = RequestBody.create(MediaType.parse("multipart/form-data"), obj.getAdhar());


            Call<FormData> call = service.putData(Token, avatar, name, mobile_no, Address, adhar, url);

            /////calling notification////
            mBuilder
                    .setContentText("Image "+String.valueOf(imageNumber+1)+" upload in progress")
                    .setProgress(0, 0, true);
            // Displays the progress bar for the first time.
            mNotifyManager.notify(imageNumber, mBuilder.build());


            call.enqueue(new Callback<FormData>() {
                @Override
                public void onResponse(Call<FormData> call, retrofit2.Response<FormData> response) {

                    if (response.code() == 200) {
                        // Log.d("Upload Successfull",response.message()+" "+String.valueOf(response.code()));

                        if (obj.getImage1Uri() != null && obj.getImage2Uri() != null && obj.getImage3Uri() != null && obj.getImage4Uri() != null) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(parentView_appActivity, "Successfully Uploaded", Snackbar.LENGTH_SHORT).show();
                                }
                            });

                            SharedPreferences.Editor ed = storeObjectShared.edit();
                            ed.remove(obj.getAdhar());
                            ed.apply();
                        }

                        ///stopping notification////
                        mBuilder.setContentText("Image "+String.valueOf(imageNumber+1)+" upload complete")
                                // Removes the progress bar
                                .setProgress(0, 0, false);
                        mNotifyManager.notify(imageNumber, mBuilder.build());


                    } else {

                        if (obj.getImage1Uri() != null && obj.getImage2Uri() != null && obj.getImage3Uri() != null && obj.getImage4Uri() != null) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Snackbar.make(parentView_appActivity, "Error uploading data!", Snackbar.LENGTH_SHORT).show();
                                }
                            });

                            Gson gson = new Gson();
                            String object_pass = gson.toJson(obj);

                            SharedPreferences.Editor ed = storeObjectShared.edit();
                            ed.putString(obj.getAdhar(), object_pass);
                            ed.commit();
                        }

                        // Log.d("Upload error",response.message()+" "+String.valueOf(response.code()));

                        ///stopping notification////
                        mBuilder.setContentText("Image "+String.valueOf(imageNumber+1)+" Upload error")
                                // Removes the progress bar
                                .setProgress(0, 0, false);
                        mNotifyManager.notify(1, mBuilder.build());
                    }

                }

                @Override
                public void onFailure(Call<FormData> call, Throwable t) {

                    if (obj.getImage1Uri() != null && obj.getImage2Uri() != null && obj.getImage3Uri() != null && obj.getImage4Uri() != null) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Snackbar.make(parentView_appActivity, "Network error!", Snackbar.LENGTH_SHORT).show();
                            }
                        });

                        Gson gson = new Gson();
                        String object_pass = gson.toJson(obj);

                        SharedPreferences.Editor ed = storeObjectShared.edit();
                        ed.putString(obj.getAdhar(), object_pass);
                        ed.commit();
                    }


                    // Log.d("error",t.getMessage());

                    ///stopping notification////
                    mBuilder.setContentText("Image "+String.valueOf(imageNumber+1)+" upload error")
                            // Removes the progress bar
                            .setProgress(0, 0, false);
                    mNotifyManager.notify(1, mBuilder.build());
                }
            });

        }
    }

}