package com.propellerads.qa.pages.login;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.propellerads.qa.pages.base.BasePage;
import lombok.Getter;
import lombok.Setter;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
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

    public LoginPage open() {
        Selenide.open(Configuration.baseUrl + "/index.html");
        return this;
    }

    public LoginPage login(String user, String password) {
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

}
