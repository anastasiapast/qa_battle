package com.propellerads.qa.tests.base;

import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    public static void setUp(){
        Configuration.browser = Browsers.CHROME;
        Configuration.baseUrl = "http://localhost:8080";
    }
}
