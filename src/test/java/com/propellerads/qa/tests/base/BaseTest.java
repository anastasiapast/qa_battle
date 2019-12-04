package com.propellerads.qa.tests.base;

import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    public static void setUp(){
        Configuration.browser = Browsers.CHROME;
        Configuration.baseUrl = "http://localhost:8080";
    }

    @AfterEach
    public void close(){
        Selenide.close();
    }
}
