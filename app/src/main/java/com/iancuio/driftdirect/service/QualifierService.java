package com.iancuio.driftdirect.service;

import com.iancuio.driftdirect.customObjects.round.Round;
import com.iancuio.driftdirect.customObjects.round.qualifier.Qualifier;
import com.iancuio.driftdirect.utils.RestUrls;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Soulstorm on 12/26/2015.
 */
public interface QualifierService {
    @GET(RestUrls.QUALIFIER_ID)
    Call<Qualifier> getQualifier(@Path("id") Long id);
}
