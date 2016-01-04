package com.iancuio.driftdirect.service;

import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.round.qualifier.JudgeQualifierAwards;
import com.iancuio.driftdirect.customObjects.round.qualifier.Qualifier;
import com.iancuio.driftdirect.customObjects.round.qualifier.QualifierJudge;
import com.iancuio.driftdirect.customObjects.round.qualifier.run.JudgeAwardedPoints;
import com.iancuio.driftdirect.utils.RestUrls;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Soulstorm on 12/26/2015.
 */
public interface QualifierService {
    @GET(RestUrls.QUALIFIER_ID)
    Call<Qualifier> getQualifier(@Path("id") Long id);

    @GET(RestUrls.QUALIFIER_ID_START)
    Call<QualifierJudge> getQualifierJudge(@Header("Authorization") String token, @Path("id") Long id);

    @POST(RestUrls.QUALIFIER_ID_SUBMIT)
    Call<JudgeQualifierAwards> postJudgeAwardedPoints(@Header("Authorization") String Token, @Body JudgeQualifierAwards judgeQualifierAwards, @Path("id") Long id, @Path("runId") Long runId);
}
