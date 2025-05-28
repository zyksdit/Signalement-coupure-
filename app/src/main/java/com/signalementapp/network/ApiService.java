package com.signalementapp.network;

import com.signalementapp.models.Signalement;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface ApiService {

    @GET("/api/signalements")
    Call<List<Signalement>> getAllSignalements();

    @POST("/api/signalements")
    Call<Void> creerSignalement(@Body Signalement signalement);
}
