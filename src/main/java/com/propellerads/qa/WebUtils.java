package com.propellerads.qa;

public class WebUtils {

    public static String correctUser = "test";
    public static String correctPassword = "test";

    public static String skipAuthUrl() {
        return "http://" + correctUser + ":" + correctPassword + "@localhost:8080";
    }
}
