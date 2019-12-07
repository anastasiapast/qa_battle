package com.propellerads.qa.pages.login;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.propellerads.qa.pages.base.BasePage;
import io.qameta.allure.Step;
import lombok.Getter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Getter
public class LoginPage extends BasePage {

    private SelenideElement loginInput = $("#loginInput");

    private SelenideElement passwordInput = $("#passwordInput");

    private SelenideElement formInputLogin = $$(".form-group").findBy(attribute("onclick", "startInputLogin()"));

    private SelenideElement formInputPassword = $$(".form-group").findBy(attribute("onclick", "startInputPassword()"));

    private SelenideElement hoverButton = $$(".btn-primary").findBy(text("Hover me faster!"));

    private SelenideElement waitButton = $$(".btn-primary").findBy(text("Wait for some time..."));

    private SelenideElement signIn = $(".card-footer img");

    public String loginUrl = Configuration.baseUrl + "/index.html";

    @Step("Open Login page")
    public LoginPage open() {
        Selenide.open(loginUrl);
        return this;
    }

    @Step("Set user = {0}; password = {1}")
    public LoginPage setFields(String user, String password) {
        formInputLogin.click();
        setField(loginInput, user);
        formInputPassword.click();
        setField(passwordInput, password);
        return this;
    }

    @Step("Hover 'Hover me faster' button")
    public LoginPage hoverButton(){
        hoverButton.shouldBe(visible.because("'Hover me faster' button is not visible")).hover();
        waitButton.waitUntil(appear, 5000);
        return this;
    }

    @Step("Click Sign in button")
    public LoginPage clickSignIn(){
        signIn.waitUntil(appear, 10000);
        signIn.shouldBe(visible.because("'Sign In' button is not visible")).click();
        return this;
    }

}
