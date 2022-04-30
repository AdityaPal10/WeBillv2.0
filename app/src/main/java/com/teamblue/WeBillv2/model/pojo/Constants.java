package com.teamblue.WeBillv2.model.pojo;

public class Constants {

    public static final String API_URL = "http://webill-prod.us-east-1.elasticbeanstalk.com/";
    public static final String VERYFI_API_URL = "http://webill-flask.herokuapp.com/";
    public static final int RESPONSE_OK = 200;
    public static final int FRIEND_EXISTS = 409;
    public static final int NO_FRIEND_EXISTS = 402;
    public static final int RESPONSE_ERROR = 400;
    public static final int NO_FRIEND_BALANCE_EXISTS=404;
    public static final String PREFERENCES_FILE_NAME = "WEBILL_PREFERENCES";
    public static final String USERNAME_KEY = "Username";
    public static final String BALANCE_TO_PAY = "balance_to_pay";
    public static final String BALANCE_TO_TAKE = "balance_to_take";
    public static final String VERYFI_CLIENTID = "vrfSn8VEgeTcN7GaH2DNsatdiS1tehnoN3YGlWv";
    public static final String VERYFI_APIKEY = "apikey zeqi2018:464a8b9601e04620209fc7dfc3294ec3";
    public static final String VERYFI_ENVURL = "https://api.veryfi.com/";
}
