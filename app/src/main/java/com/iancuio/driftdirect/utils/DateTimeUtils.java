package com.iancuio.driftdirect.utils;

import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 * Created by Soulstorm on 1/23/2016.
 */
public class DateTimeUtils {
    public static boolean eventIsNow(DateTime startHour, DateTime endHour) {
        return new Interval( startHour, endHour).contains(DateTime.now());
    }
}
