package com.axr.sjoerd.android.Service;

public class Config {

    private static final boolean releaseServer = false;

    // 10.0.2.2 is de Android connectie naar localhost.
    public static final String BASE_SERVICE_URL = releaseServer ? "https://release-prog4-heroku.com": "http://10.0.2.2:3000";

    public static final String LOCAL_ENC_KEY = "SuperSecureKey";
    public static final String BASE_API = "/api";
    public static final String URL_LOGIN = BASE_SERVICE_URL +  BASE_API + "/user/login";
    public static final String URL_REGISTER = BASE_SERVICE_URL + BASE_API + "/user/register";


    public static final String TOKEN_NAME = "TOKEN";
    public static final String NO_TOKEN = "NOTOKEN";
}
