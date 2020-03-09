package com.example.studentdatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface StudentInterface {

    @GET("phpFolder/student.php")
    Call<List<Student>> getStudents();

    @POST("phpFolder/sendData.php")
    Call<String> addStudent(@Query("name") String name,
                            @Query("mobile") String mobile,
                            @Query("address") String address,
                            @Query("id") String id);




    @PUT("phpFolder/updateData.php")
    Call<String> updateStudent(@Query("name") String name,
                               @Query("mobile") String mobile,
                               @Query("address") String address,
                               @Query("id") String id);

    @DELETE("phpFolder/deleteData.php")
    Call<String> deleteStudent(@Query("id") String id);
}
