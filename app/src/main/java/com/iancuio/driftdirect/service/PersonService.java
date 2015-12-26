package com.iancuio.driftdirect.service;

import com.iancuio.driftdirect.customObjects.championship.ChampionshipShort;
import com.iancuio.driftdirect.customObjects.person.Person;
import com.iancuio.driftdirect.utils.RestUrls;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Soulstorm on 11/30/2015.
 */
public interface PersonService {
    @GET(RestUrls.PERSON_ID)
    Call<Person> getPerson(@Path("id") Long id);
}
