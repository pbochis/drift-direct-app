package com.iancuio.driftdirect.service;

import com.iancuio.driftdirect.customObjects.person.Person;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.utils.RestUrls;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Soulstorm on 12/5/2015.
 */
public interface RoundService {
    @GET(RestUrls.ROUND_ID)
    Call<Round> getRound(@Path("id") Long id);
}
