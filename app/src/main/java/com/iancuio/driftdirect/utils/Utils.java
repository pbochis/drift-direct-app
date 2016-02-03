package com.iancuio.driftdirect.utils;

import com.iancuio.driftdirect.utils.interfaces.NullCheck;

/**
 * Created by Soulstorm on 11/21/2015.
 */
public class Utils {

    public static void nullCheck(Object object, NullCheck nullCheck) {
        if (object != null) {
            nullCheck.onNotNull();
        } else {
            nullCheck.onNull();
        }
    }
}
