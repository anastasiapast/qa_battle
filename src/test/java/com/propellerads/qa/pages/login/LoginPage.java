package com.propellerads.qa.pages.login;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.propellerads.qa.pages.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Getter
@Setter
public class LoginPage extends BasePage {

    private SelenideElement loginInput = $("#loginInput");

    private SelenideElement passwordInput = $("#passwordInput");

    private SelenideElement formInputLogin = $$(".form-group").findBy(attribute("onclick", "startInputLogin()"));

    private SelenideElement formInputPassword = $$(".form-group").findBy(attribute("onclick", "startInputPassword()"));

    private SelenideElement hoverButton = $$(".btn-primary").findBy(text("Hover me faster!"));

    private SelenideElement waitButton = $$(".btn-primary").findBy(text("Wait for some time..."));

    private SelenideElement signIn = $(".card-footer img");

    public String loginUrl = Configuration.baseUrl + "/index.html";

    public LoginPage open() {
        Selenide.open(loginUrl);
        return this;
    }

    public LoginPage setFields(String user, String password) {
        formInputLogin.click();
        loginInput.click();
        loginInput.clear();
        loginInput.setValue(user);
        formInputPassword.click();
        passwordInput.click();
        passwordInput.clear();
        passwordInput.setValue(password);
        return this;
    }

    public LoginPage hoverButton(){
        hoverButton.shouldBe(visible.because("'Hover me faster' button is not visible")).hover();
        waitButton.waitUntil(appear, 5000);
        return this;
    }

    public LoginPage clickSignIn(){
        signIn.waitUntil(appear, 10000);
        signIn.shouldBe(visible.because("'Sign In' button is not visible")).click();
        return this;
    }

}
