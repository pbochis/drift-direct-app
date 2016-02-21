package com.iancuio.driftdirect.service;

import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.customObjects.championship.ChampionshipShort;
import com.iancuio.driftdirect.customObjects.championship.driver.ChampionshipDriverParticipation;
import com.iancuio.driftdirect.customObjects.championship.judge.ChampionshipJudgeParticipation;
import com.iancuio.driftdirect.customObjects.person.PersonShort;
import com.iancuio.driftdirect.customObjects.round.RoundShort;
import com.iancuio.driftdirect.utils.RestUrls;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Created by Soulstorm on 11/28/2015.
 */
public interface ChampionshipService {

    @GET(RestUrls.CHAMPIONSHIP +"/short")
    Call<List<ChampionshipShort>> getChampionshipsDetails();

    @GET(RestUrls.CHAMPIONSHIP +"/short")
    Call<List<ChampionshipShort>> getChampionshipsDemoDetails(@Header("Authorization") String token);

    @GET(RestUrls.CHAMPIONSHIP_ID)
    Call<Championship> getChampionshipsFull(@Path("id") Long id);

    @GET(RestUrls.CHAMPIONSHIP_ID_ROUNDS)
    Call<List<RoundShort>> getChampionshipRounds(@Path("id") Long id);

    @GET(RestUrls.CHAMPIONSHIP_ID_DRIVERS)
    Call<List<PersonShort>> getDrivers(@Path("id") Long id);

    @GET(RestUrls.CHAMPIONSHIP_ID_DRIVERS_ID)
    Call<ChampionshipDriverParticipation> getDriver(@Path("championshipId") Long championshipId, @Path("driverId") Long driverId);

    @GET(RestUrls.CHAMPIONSHIP_ID_JUDGES)
    Call<List<ChampionshipJudgeParticipation>> getJudges(@Path("id") Long id);

}
