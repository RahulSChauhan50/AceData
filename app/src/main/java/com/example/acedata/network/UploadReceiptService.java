package com.example.acedata.network;

import com.example.acedata.FormData;

import java.util.List;
import java.util.ListIterator;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UploadReceiptService{

    @Multipart
    @POST("/owner/")
    Call<FormData> uploadReceipt(
            @Header("Authorization") String Token,
            @Part MultipartBody.Part file,
            @Part ("name") RequestBody name,
            @Part ("mobile_no") RequestBody mobile_no,
            @Part ("Address") RequestBody Address,
            @Part ("adhar") RequestBody adhar

    );

    @GET("/owner/")
    Call<List<FormData>> getDataList(
            @Header("Authorization") String Token
    );

    @FormUrlEncoded
    @POST("/api/v1/rest-auth/login/")
    Call<FormData> signin(
            @Field("username") String username,
            @Field ("password") String password
    );
}