package com.iancuio.driftdirect.utils;

public class RestUrls {

    //Cloud server
    //public static final String BASE_URL = "https://driftdirect.herokuapp.com";
    //Pilu server
    //public static final String BASE_URL = "http://192.168.1.101:8080/driftdirect/";
    //Local developement
    public static final String BASE_URL = "http://192.168.1.101:8080/";
    //Online
    //public static final String BASE_URL = "http://46.101.120.89:8080/driftdirect/";

    public static final String FILE = BASE_URL + "file/";
    //192.168.1.104:8080

    public static final String CHAMPIONSHIP = "championship";
    public static final String CHAMPIONSHIP_ID = "championship/{id}";
    public static final String CHAMPIONSHIP_ID_ROUNDS = "championship/{id}/rounds";
    public static final String CHAMPIONSHIP_ID_DRIVERS = "championship/{id}/drivers";
    public static final String CHAMPIONSHIP_ID_DRIVERS_ID = "championship/{championshipId}/drivers/{driverId}";
    public static final String CHAMPIONSHIP_ID_JUDGES = "championship/{id}/judges";

    public static final String ROUND = "round";
    public static final String ROUND_ID = "round/{id}";
    public static final String ROUND_ID_SHCEDULE = "round/{id}/schedule";
    public static final String ROUND_ID_PLAYOFFS = "round/{id}/playoffs";

    public static final String QUALIFIER_ID = "qualifier/{id}";
    public static final String QUALIFIER_ID_START = "qualifier/{id}/start";
    public static final String QUALIFIER_ID_SUBMIT = "qualifier/{id}/submit/run/{runId}";

    public static final String PLAYOFF_ID_START = "playoff/battle/{battleId}/start";
    public static final String PLAYOFF_ID_SUBMIT = "playoff/battle/{battleId}/submit";
    public static final String PLAYOFF_BATTLE_ID = "playoff/battle/{battleId}";

    public static final String USER = "user";

    public static final String PERSON = "person";
    public static final String PERSON_ID = "person/{id}";

    public static final String PERSON_DRIVER_DETAILS = "person/driver";

    public static final String SPONSOR = "sponsor";
    public static final String SPONSOR_ID = "sponspor/{id}";

    public static final String TEAM = "team";
    public static final String TEAM_ID = "team/{id}";

    public static final String SEND_GCM_TOKEN = "gcm/register";


}