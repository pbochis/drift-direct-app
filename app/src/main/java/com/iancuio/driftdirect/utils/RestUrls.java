package com.iancuio.driftdirect.utils;

public class RestUrls {

    //Cloud server
    //public static final String BASE_URL = "https://driftdirect.herokuapp.com";
    //Pilu server
    public static final String BASE_URL = "http://192.168.1.106:8080";
    //Local developement
    //public static final String BASE_URL = "http://192.168.1.100:8080";

    public static final String FILE = BASE_URL + "/file/";
    //192.168.1.104:8080

    public static final String CHAMPIONSHIP = "/championship";
    public static final String CHAMPIONSHIP_ID = "/championship/{id}";
    public static final String CHAMPIONSHIP_ID_ROUNDS = "/championship/{id}/rounds";
    public static final String CHAMPIONSHIP_ID_DRIVERS = "/championship/{id}/drivers";
    public static final String CHAMPIONSHIP_ID_DRIVERS_ID = "/championship/{championshipId}/drivers/{driverId}";
    public static final String CHAMPIONSHIP_ID_JUDGES = "/championship/{id}/judges";

    public static final String ROUND = "/round";
    public static final String ROUND_ID = "/round/{id}";
    public static final String ROUND_ID_SHCEDULE = "/round/{id}/schedule";

    public static final String USER = "/user";

    public static final String PERSON = "/person";
    public static final String PERSON_ID = "/person/{id}";

    public static final String PERSON_DRIVER_DETAILS = "/person/driver";

    public static final String SPONSOR = "/sponsor";
    public static final String SPONSOR_ID = "/sponspor/{id}";

    public static final String TEAM = "/team";
    public static final String TEAM_ID = "/team/{id}";

}