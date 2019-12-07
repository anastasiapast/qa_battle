package com.propellerads.qa.tests.base;

import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.FileDownloadMode.PROXY;

public class BaseTest {

    public static String correctUser = "test";
    public static String correctPassword = "test";

    @BeforeAll
    public static void setUp() {
        Configuration.browser = Browsers.CHROME;
        Configuration.startMaximized = true;
        Configuration.baseUrl = "http://localhost:8080";
        Configuration.fileDownload = PROXY;
        Configuration.proxyEnabled = true;
        Configuration.reportsFolder = "build/reports/tests";
        Configuration.browserCapabilities.setCapability("profile.default_content_settings.popups", "0");
        Configuration.browserCapabilities.setCapability("plugins.always_open_pdf_externally", true);
        Configuration.browserCapabilities.setCapability("download.prompt_for_download", false);
        Configuration.browserCapabilities.setCapability("download.default_directory", "build/reports/tests");
        SelenideLogger.addListener("allure", new AllureSelenide());
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

    @AfterAll
    public static void tearDown(){
        SelenideLogger.removeListener("allure");
    }

}
