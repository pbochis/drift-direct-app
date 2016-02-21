package com.iancuio.driftdirect.utils.gcm.logic;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GcmPubSub;
import com.iancuio.driftdirect.utils.gcm.topics.Events;
import com.iancuio.driftdirect.utils.gcm.topics.NotificationScope;

import java.io.IOException;

/**
 * Created by Soulstorm on 2/20/2016.
 */
public class SubscribeBattlesTopic extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private static final String TAG = "SubQual";

    public SubscribeBattlesTopic() {
        super(TAG);
    }

    public void subscribeBattleUpdate(Long roundId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("gcmToken", "");

        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        try {
            String topicFull = "/topics/" + NotificationScope.ROUND + "-" + roundId + "-" + Events.BATTLES;
            pubSub.subscribe(token, topicFull, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unsubscribeBattleUpdate(Long roundId) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("gcmToken", "");

        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        try {
            String topicFull = "/topics/" + NotificationScope.ROUND + "-" + roundId + "-" + Events.BATTLES;
            pubSub.unsubscribe(token, topicFull);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.hasExtra("subBattles")) {
            subscribeBattleUpdate(intent.getLongExtra("roundId", 0));
        } else if (intent.hasExtra("unsubBattles")) {
            unsubscribeBattleUpdate(intent.getLongExtra("roundId", 0));
        }
    }
}
