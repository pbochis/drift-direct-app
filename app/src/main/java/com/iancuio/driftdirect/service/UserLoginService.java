package com.iancuio.driftdirect.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.iancuio.driftdirect.customObjects.User;
import com.iancuio.driftdirect.utils.RestUrls;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Created by Soulstorm on 11/16/2015.
 */
public interface UserLoginService {

    @GET(RestUrls.USER)
    Call<User> getUserDetails(@Header("Authorization") String token);
}
