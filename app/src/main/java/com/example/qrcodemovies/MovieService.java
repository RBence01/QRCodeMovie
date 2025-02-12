package com.example.qrcodemovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MovieService {
    @GET("movie")
    Call<List<Movie>> getAll();
    
    @GET("movie/{id}")
    Call<Movie> getById(@Path("id") int id);

    @POST("movie/")
    Call<Movie> crate(@Body Movie movie);

    @PUT("movie/{id}")
    Call<Movie> update(@Path("id") int id, @Body Movie movie);

    @DELETE("movie/{id}")
    Call<Void> delete(@Path("id") int id);
}
