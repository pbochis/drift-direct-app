package com.iancuio.driftdirect.utils.gcm.logic; /**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.app.IntentService;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.iancuio.driftdirect.R;
import com.iancuio.driftdirect.championship.navigationView.calendar.fragments.CalendarFragment;
import com.iancuio.driftdirect.customObjects.championship.Championship;
import com.iancuio.driftdirect.service.ChampionshipService;
import com.iancuio.driftdirect.service.GcmService;
import com.iancuio.driftdirect.utils.FragmentUtils;
import com.iancuio.driftdirect.utils.RestUrls;

import java.io.IOException;

import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.e(TAG, "GCM Registration Token: " + token);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("gcmToken",token);
            editor.apply();



            // TODO: Implement this method to send any registration to your app's servers.

            sendRegistrationToServer(token);

            // Subscribe to topic channels
            //subscribeTopics(token);

            // You should store a boolean that indicates whether the generated token has been
            // sent to your server. If the boolean is false, send the token to your server,
            // otherwise your server should have already received the token.
            // [END register_for_gcm]
        } catch (Exception e) {
            Log.e(TAG,  e.toString());
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(RestUrls.BASE_URL).addConverterFactory(JacksonConverterFactory.create()).build();

            GcmService gcmService;
            gcmService = retrofit.create(GcmService.class);

            Call<Void> gcmCall = gcmService.postGcmToken(token);

        gcmCall.enqueue(new retrofit.Callback<Void>() {
                @Override
                public void onResponse(final Response<Void> response, Retrofit retrofit) {
                    Log.e("gcm server", "mere");
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    Log.e("gcm server", t.toString());
                }
            });
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]

    // [END subscribe_topics]

}
