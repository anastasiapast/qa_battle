package com.propellerads.qa.tests.login;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import com.propellerads.qa.pages.login.LoginPage;
import com.propellerads.qa.tests.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;


public class LoginTest extends BaseTest {

    LoginPage loginPage = new LoginPage();

    @DisplayName("")
    @Test
    void correctLogin(){
        loginPage.open();
        loginPage.login("test", "test");
        loginPage.getHoverButton().shouldBe(visible.because("'Hover me faster' button is not visible")).hover();
        loginPage.getWaitButton().waitUntil(appear, 5000);
        loginPage.getSignIn().waitUntil(appear, 10000);
        loginPage.getSignIn().shouldBe(visible.because("'Sign In' button is not visible")).click();

        //wait until alert - https://seleniumatfingertips.wordpress.com/2016/07/05/check-whether-alert-is-present-on-the-webpage-or-not/
        WebDriverRunner.getWebDriver().switchTo().alert().accept();
        WebDriverRunner.getWebDriver().switchTo().alert().accept();

        int a = 10;

        //https://medium.com/@rosolko/fast-authorization-level-local-storage-6c84e9b3cef1
        //https://medium.com/@rosolko/boost-you-autotests-with-fast-authorization-b3eee52ecc19
        //https://medium.com/@rosolko/simple-allure-2-configuration-for-gradle-8cd3810658dd
    }



}
