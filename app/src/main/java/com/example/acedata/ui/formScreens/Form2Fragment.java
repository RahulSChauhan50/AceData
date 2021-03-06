package com.example.acedata.ui.formScreens;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.acedata.AppActivity;
import com.example.acedata.FormData;
import com.example.acedata.MainActivity;
import com.example.acedata.R;
import com.example.acedata.location.FetchAddressTask;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Form2Fragment extends Fragment implements
        FetchAddressTask.OnTaskCompleted {
    Button btn_back, btn_next;
    Button button_selectphoto1;
    Button button_selectphoto2;
    Button button_selectphoto3;
    Button button_selectphoto4;
    TextView textView_image1data;
    TextView textView_image2data;
    TextView textView_image3data;
    TextView textView_image4data;
    int imagenumber = -1;
    FormData obj;
    Location mLastLocation;
    FusedLocationProviderClient mFusedLocationClient;
    String[] sampleimagesinfo;
    String mCurrentPhotoPath;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View form2 = inflater.inflate(R.layout.fragment_form2, container, false);

        btn_next = form2.findViewById(R.id.open);
        button_selectphoto1 = form2.findViewById(R.id.buttonimage1);
        button_selectphoto2 = form2.findViewById(R.id.buttonimage2);
        button_selectphoto3 = form2.findViewById(R.id.buttonimage3);
        button_selectphoto4 = form2.findViewById(R.id.buttonimage4);
        textView_image1data = form2.findViewById(R.id.textViewimage1);
        textView_image2data = form2.findViewById(R.id.textViewimage2);
        textView_image3data = form2.findViewById(R.id.textViewimage3);
        textView_image4data = form2.findViewById(R.id.textViewimage4);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        sampleimagesinfo = new String[4];

        return form2;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ////receiving values from form1
        Bundle arguments = getArguments();
        String desired_string = arguments.getString("form2_pass");

        //converting string data back to object
        Gson gson = new Gson();
        obj = gson.fromJson(desired_string, FormData.class);

        button_selectphoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagenumber = 0;
                capture_function();

            }
        });
        button_selectphoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagenumber = 1;
                capture_function();
            }
        });
        button_selectphoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagenumber = 2;
                capture_function();
            }
        });
        button_selectphoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagenumber = 3;
                capture_function();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((AppActivity)getActivity()).Add_Form3(view);
                Log.d("form2", obj.getAddress() + " " + obj.getName() + " " + obj.getMobile_no() + " " + obj.getAdhar());
            }
        });
    }

    void capture_function() {
        final int REQUEST_IMAGE_CAPTURE = 1;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();

            }
            // Continue only if the File was successfully created

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.acedata.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE); //Deprecated
                someActivityResultLauncher.launch(takePictureIntent);
            }

        }
    }

    //creating a temporary file for image
    private File createImageFile() throws IOException {
        // Create an image file name
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmmss_");
        String timeStamp = sdf.format(new Date());
        String imageFileName = timeStamp;


        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpeg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        Form2Fragment.this.mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        getLocation_function();

                    } else {
                        Toast.makeText(getContext(), "Something Went Wrong !!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    @SuppressLint("MissingPermission")
    void getLocation_function() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            mLastLocation = location;
                            new FetchAddressTask(getContext(),
                                    Form2Fragment.this).execute(location);
                            Log.d("Location", String.valueOf(mLastLocation.getLatitude()) + " " + String.valueOf(mLastLocation.getLongitude()));

                        } else {
                            Toast.makeText(getContext(), "Error while fetching location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onTaskCompleted(String result) {

    }
}
