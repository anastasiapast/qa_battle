package com.propellerads.qa.tests.login;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.propellerads.qa.pages.login.LoginPage;
import com.propellerads.qa.tests.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    LoginPage loginPage = new LoginPage();

    @DisplayName("Check login with correct credentials")
    @Test
    void checkCorrectCredentials(){
        loginPage.open();
        loginPage.setFields(correctUser, correctPassword);
        loginPage.hoverButton();
        loginPage.clickSignIn();

        Selenide.switchTo().alert().accept();
        Selenide.switchTo().alert().accept();

        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 5000);
        wait.until(condition -> WebDriverRunner.url().contains("main.html"));
    }

    private static Stream<Arguments> emptyCredentials() {
        return Stream.of(
                Arguments.of("", ""),
                Arguments.of(correctUser, ""),
                Arguments.of("", correctPassword));
    }

    @ParameterizedTest(name = "Check login with empty credentials")
    @MethodSource("emptyCredentials")
    void checkEmptyCredentials(String user, String password){
        loginPage.open();
        loginPage.setFields(user, password);
        loginPage.getHoverButton().shouldBe(visible
                .because("'Hover me faster' button is not visible")).hover();
        loginPage.getHoverButton().shouldBe(disabled
                .because("'Hover me faster' button should be disabled for user = " + user + " ; password = " + password));
    }

    private static Stream<Arguments> invalidCredentials() {
        return Stream.of(
                Arguments.of("user123", correctPassword),
                Arguments.of(correctUser, "qwerty"),
                Arguments.of("user123", "qwerty"));
    }

    @ParameterizedTest(name = "Check login with incorrect credentials")
    @MethodSource("invalidCredentials")
    void checkInvalidCredentials(String user, String password){
        loginPage.open();
        loginPage.setFields(user, password);
        loginPage.hoverButton();
        loginPage.clickSignIn();

        Selenide.switchTo().alert().accept();
        Selenide.switchTo().alert().accept();

        assertTrue(WebDriverRunner.url().contains("loginError.html"), "Error page is not loaded for wrong credentials");
    }

    @DisplayName("Check first alert dismiss")
    @Test
    void checkFirstAlertDismiss(){
        loginPage.open();
        loginPage.setFields(correctUser, correctPassword);
        loginPage.hoverButton();
        loginPage.clickSignIn();

        Selenide.switchTo().alert().dismiss();
        assertEquals(loginPage.loginUrl, WebDriverRunner.url(),  WebDriverRunner.url() + " is open instead of login page after first alert dismissing");
    }

    @DisplayName("Check first alert dismiss")
    @Test
    void checkSecondAlertDismiss(){
        loginPage.open();
        loginPage.setFields(correctUser, correctPassword);
        loginPage.hoverButton();
        loginPage.clickSignIn();

        Selenide.switchTo().alert().accept();
        Selenide.switchTo().alert().dismiss();
        assertTrue(WebDriverRunner.url().contains("loginError.html"), "Error page is not loaded after second alert dismissing");
    }

    @DisplayName("Clear credentials and click Sign in")
    @Test
    void checkClearAndSignIn(){
        loginPage.open();
        loginPage.setFields(correctUser, correctPassword);
        loginPage.hoverButton();
        loginPage.getFormInputLogin().click();
        loginPage.getLoginInput().clear();
        loginPage.getFormInputPassword().click();
        loginPage.getPasswordInput().clear();
        loginPage.getSignIn().shouldBe(disabled.because("'Sign In' button should be disabled for empty credentials"));
    }

    @DisplayName("Check Tab for user credentials form")
    @Test
    void checkTabForCredentialsForm(){
        loginPage.open();
        loginPage.getFormInputLogin().click();
        loginPage.getLoginInput().click();
        loginPage.getLoginInput().sendKeys(Keys.TAB);
        loginPage.getPasswordInput().shouldBe(focused.because("Password field should be focused after TAB key pressing"));
    }
}
