package com.iancuio.driftdirect.service;

import com.iancuio.driftdirect.customObjects.person.Person;
import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.round.playoffs.PlayoffTreeGraphicDisplay;
import com.iancuio.driftdirect.customObjects.round.playoffs.battle.PlayoffBattleSubmitJudgeBattle;
import com.iancuio.driftdirect.customObjects.round.playoffs.battle.PlayoffBattleViewJudgeBattle;
import com.iancuio.driftdirect.customObjects.round.playoffs.battle.PlayoffBattleViewPublicBattle;
import com.iancuio.driftdirect.utils.RestUrls;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Soulstorm on 12/5/2015.
 */
public interface RoundService {
    @GET(RestUrls.ROUND_ID)
    Call<Round> getRound(@Path("id") Long id);

    @GET(RestUrls.ROUND_ID_PLAYOFFS)
    Call<PlayoffTreeGraphicDisplay> getPlayoffs(@Header("Authorization") String Token, @Path("id") Long id);

    @GET(RestUrls.PLAYOFF_ID_START)
    Call<PlayoffBattleViewJudgeBattle> getBattleDetailsForJudge(@Header("Authorization") String token, @Path("battleId") Long id);

    @POST(RestUrls.PLAYOFF_ID_SUBMIT)
    Call<Void> postJudgeBattleAwards(@Header("Authorization") String token, @Path("battleId") Long battleId, @Body PlayoffBattleSubmitJudgeBattle battleRoundJudging);

    @GET(RestUrls.PLAYOFF_BATTLE_ID)
    Call<PlayoffBattleViewPublicBattle> getBattleDetailsForPublic(@Path("battleId") Long id);
}
