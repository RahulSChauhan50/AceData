package com.example.acedata.network;

import com.example.acedata.FormData;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface UploadReceiptService{

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

    @Multipart
    @PUT
    Call<FormData> putData(
            @Header("Authorization") String Token,
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2,
            @Part MultipartBody.Part file3,
            @Part MultipartBody.Part file4,
            @Part ("name") RequestBody name,
            @Part ("mobile_no") RequestBody mobile_no,
            @Part ("Address") RequestBody Address,
            @Part ("adhar") RequestBody adhar,
            @Url String url

    );
}