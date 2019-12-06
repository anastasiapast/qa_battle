package com.propellerads.qa.tests.base;

import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;

public class BaseTest {

    public static String correctUser = "test";
    public static String correctPassword = "test";

    @BeforeAll
    public static void setUp() {
        Configuration.browser = Browsers.CHROME;
        Configuration.baseUrl = "http://localhost:8080";
    }

    public static void setAuthCookies(){
        Selenide.open(Configuration.baseUrl);
        Cookie cookie = new Cookie("secret", "IAmSuperSeleniumMaster");
        WebDriverRunner.getWebDriver().manage().addCookie(cookie);
    }

    @AfterEach
    public void close() {
        Selenide.close();
    }

}
