package com.iancuio.driftdirect.utils;

import android.content.Context;
import android.content.Intent;

import com.iancuio.driftdirect.utils.gcm.logic.RegistrationIntentService;
import com.iancuio.driftdirect.utils.gcm.logic.SubscribeBattlesTopic;
import com.iancuio.driftdirect.utils.gcm.logic.SubscribeQualificationsTopic;

/**
 * Created by Soulstorm on 2/15/2016.
 */
public class ServiceUtils {

    public static void launchRegistrationService(Context context) {
        // Construct our Intent specifying the Service
        Intent i = new Intent(context, RegistrationIntentService.class);
        // Add extras to the bundle
        i.putExtra("foo", "bar");
        // Start the service
        context.startService(i);
    }

    public static void launchSubscribeQualificationsService(Context context, Long roundId) {
        // Construct our Intent specifying the Service
        Intent i = new Intent(context, SubscribeQualificationsTopic.class);
        // Add extras to the bundle
        i.putExtra("subQualifications", 1);
        i.putExtra("roundId", roundId);

        // Start the service
        context.startService(i);
    }

    public static void launchUnsubscribeQualificationsService(Context context, Long roundId) {
        // Construct our Intent specifying the Service
        Intent i = new Intent(context, SubscribeQualificationsTopic.class);
        // Add extras to the bundle
        i.putExtra("unsubQualifications", 1);
        i.putExtra("roundId", roundId);
        // Start the service
        context.startService(i);
    }

    public static void launchSubscribeBattlesService(Context context, Long roundId) {
        // Construct our Intent specifying the Service
        Intent i = new Intent(context, SubscribeBattlesTopic.class);
        // Add extras to the bundle
        i.putExtra("subBattles", 1);
        i.putExtra("roundId", roundId);
        // Start the service
        context.startService(i);
    }

    public static void launchUnsubscribeBattlesService(Context context, Long roundId) {
        // Construct our Intent specifying the Service
        Intent i = new Intent(context, SubscribeBattlesTopic.class);
        // Add extras to the bundle
        i.putExtra("unsubBattles", 1);
        i.putExtra("roundId", roundId);
        // Start the service
        context.startService(i);
    }


}
