package com.iancuio.driftdirect.service;

import com.iancuio.driftdirect.customObjects.round.qualifier.JudgeQualifierAwards;
import com.iancuio.driftdirect.utils.RestUrls;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Soulstorm on 2/15/2016.
 */
public interface GcmService {
    @POST(RestUrls.SEND_GCM_TOKEN)
    Call<Void> postGcmToken(@Query("key") String key);
}
