package com.example.acedata.ui.formScreens;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
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
import com.example.acedata.network.RetrofitClientInstance;
import com.example.acedata.network.UploadReceiptService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Form2Fragment extends Fragment implements
        FetchAddressTask.OnTaskCompleted {
    Button btn_next;
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
    private FusedLocationProviderClient mFusedLocationClient;

    SharedPreferences storeObjectShared;

    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;

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

        storeObjectShared=getActivity().getSharedPreferences("Stored_objects", Context.MODE_PRIVATE);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        mNotifyManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(getContext(),"upload_data");
        mBuilder.setContentTitle("File Upload")
                .setContentText("Upload in progress")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.drawable.ic_baseline_upload_file_24);

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

        checkForPreviouslyStoredObjects();

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
//                uploadThread thread=new uploadThread();
//                thread.start();

                ((AppActivity)getActivity()).uploadData_function(obj);

                if(obj.getImage1Uri()!=null && obj.getImage2Uri()!=null && obj.getImage3Uri()!=null && obj.getImage4Uri()!=null){
                    ((AppActivity)getActivity()).Add_Form3(view);
                }

                Log.d("form2", obj.getAddress() + " " + obj.getName() + " " + obj.getMobile_no() + " " + obj.getAdhar());
            }
        });
    }

    void checkForPreviouslyStoredObjects(){

        String storedObj=storeObjectShared.getString(obj.getAdhar(),null);

        if(storedObj!=null){
            Gson gson = new Gson();
            FormData myObj = gson.fromJson(storedObj, FormData.class);

            File newFile;
            if(myObj.getImage1Uri()!=null){
                obj.setImage1Uri(myObj.getImage1Uri());
                newFile=new File(myObj.getImage1Uri());
                String filename=newFile.getName() + "\n" + String.valueOf(newFile.length() / 1000) + " KB";
                textView_image1data.setText(filename);
            }
            if(myObj.getImage2Uri()!=null){
                obj.setImage2Uri(myObj.getImage2Uri());
                newFile=new File(myObj.getImage2Uri());
                String filename=newFile.getName() + "\n" + String.valueOf(newFile.length() / 1000) + " KB";
                textView_image2data.setText(filename);
            }
            if(myObj.getImage3Uri()!=null){
                obj.setImage3Uri(myObj.getImage3Uri());
                newFile=new File(myObj.getImage3Uri());
                String filename=newFile.getName() + "\n" + String.valueOf(newFile.length() / 1000) + " KB";
                textView_image3data.setText(filename);
            }
            if(myObj.getImage4Uri()!=null){
                obj.setImage4Uri(myObj.getImage4Uri());
                newFile=new File(myObj.getImage4Uri());
                String filename=newFile.getName() + "\n" + String.valueOf(newFile.length() / 1000) + " KB";
                textView_image4data.setText(filename);
            }

        }
    }

    void capture_function() {
        //final int REQUEST_IMAGE_CAPTURE = 1;

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

        final int currentFinalImageNumber=imagenumber;
        final String currentFinalPhotoPath=mCurrentPhotoPath;

        CancellationTokenSource cts = new CancellationTokenSource();
        mFusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cts.getToken())
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d("coordinates", String.valueOf(location.getLongitude()) + " " + String.valueOf(location.getLatitude()));
                            mLastLocation = location;
                            new FetchAddressTask(getContext(),
                                    Form2Fragment.this,currentFinalImageNumber,currentFinalPhotoPath).execute(location);
                        } else {
                            Log.d("location", "location is null");
                            Toast.makeText(getContext(),"Failed to fetch location \nCheck if GPS is turned on",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("failed", e.toString());
                        Toast.makeText(getContext(),"Failed to fetch location \nCheck if GPS is turned on",Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Override
    public void onTaskCompleted(String result,int currentImageNumber,String currentPhotoPath) {
        Log.d("current address", result);
        Log.d("imagenumber", String.valueOf(currentImageNumber)+" "+currentPhotoPath);

        watermarkThread thread=new watermarkThread(currentImageNumber,currentPhotoPath,result);
        thread.start();
    }

    class watermarkThread extends Thread{
        int currentImageNumber;
        String currentPhotoPath;
        String address;

        public watermarkThread(int currentImageNumberPassed,String currentPhotoPathPassed,String addressPassed) {
            this.currentImageNumber=currentImageNumberPassed;
            this.currentPhotoPath=currentPhotoPathPassed;
            this.address=addressPassed;
        }

        @Override
        public void run() {

            //Uri uri = data.getData();
            File file = new File(currentPhotoPath);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.fromFile(file));

                Bitmap bmpWithBorder = Bitmap.createBitmap(bitmap.getWidth() , bitmap.getHeight() + 300 * 2, bitmap.getConfig());
                Canvas canvas = new Canvas(bmpWithBorder);
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(bitmap, 0,0, null);

                // new antialiased Paint
                TextPaint paint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
                // text color - #3D3D3D
                paint.setColor(Color.BLACK);
                // text size in pixels
                paint.setTextSize(125);
                // text shadow
                paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

                // set text width to canvas width
                int textWidth = canvas.getWidth() - 50;
                ///////////////////text//////////
                String gText=address;
                // init StaticLayout for text
                StaticLayout textLayout = new StaticLayout(
                        gText, paint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);


                // get position of text's top left corner
                float x = (bmpWithBorder.getWidth() - textWidth)/2;
                float y = bitmap.getHeight()+50;

                // draw text to the Canvas
                canvas.save();
                canvas.translate(x, y);
                textLayout.draw(canvas);
                canvas.restore();
                //////////////////////////

                //////saving compressed bitmap////
                SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmmss_");
                String timeStamp = sdf.format(new Date());
                String imageFileName = "Compressed_" + timeStamp;

                File dir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File imagecompress = File.createTempFile(
                        imageFileName,
                        ".jpeg",
                        dir
                );

                //getting path of compressed image///////
                switch (currentImageNumber){
                    case 0:{
                        obj.setImage1Uri(imagecompress.getAbsolutePath());
                        break;
                    }
                    case 1:{
                        obj.setImage2Uri(imagecompress.getAbsolutePath());
                        break;
                    }
                    case 2:{
                        obj.setImage3Uri(imagecompress.getAbsolutePath());
                        break;
                    }
                    case 3:{
                        obj.setImage4Uri(imagecompress.getAbsolutePath());
                        break;
                    }

                }

                ///////saving compressed image///////
                try (FileOutputStream out = new FileOutputStream(imagecompress)) {
                    bmpWithBorder.compress(Bitmap.CompressFormat.JPEG, 50, out);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                /////////////////////////////////

                /////fetching file info////
                File tempCompressedFile;

                switch (currentImageNumber){
                    case 0:{
                        tempCompressedFile=new File(obj.getImage1Uri());
                        break;
                    }
                    case 1:{
                        tempCompressedFile=new File(obj.getImage2Uri());
                        break;
                    }
                    case 2:{
                        tempCompressedFile=new File(obj.getImage3Uri());
                        break;
                    }
                    case 3:{
                        tempCompressedFile=new File(obj.getImage4Uri());
                        break;
                    }

                    default:
                        throw new IllegalStateException("Unexpected value: " + currentImageNumber);
                }

                sampleimagesinfo[currentImageNumber] = tempCompressedFile.getName() ;
                String filename=tempCompressedFile.getName() + "\n" + String.valueOf(tempCompressedFile.length() / 1000) + " KB";
                //////////////////////////

                switch (currentImageNumber) {
                    case 0: {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView_image1data.setText(filename);
                            }
                        });

                        Log.d("obj path",obj.getImage1Uri());
                        break;
                    }

                    case 1: {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView_image2data.setText(filename);
                            }
                        });
                        Log.d("obj path",obj.getImage2Uri());
                        break;
                    }

                    case 2: {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView_image3data.setText(filename);
                            }
                        });
                        Log.d("obj path",obj.getImage3Uri());
                        break;
                    }
                    case 3: {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView_image4data.setText(filename);
                            }
                        });
                        Log.d("obj path",obj.getImage4Uri());
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
