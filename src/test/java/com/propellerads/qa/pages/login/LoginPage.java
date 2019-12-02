package com.propellerads.qa.pages.login;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.propellerads.qa.pages.base.BasePage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LoginPage extends BasePage {

    public SelenideElement loginInput = $("#loginInput");

    public SelenideElement passwordInput = $("#passwordInput");

    public SelenideElement formInput = $$(".form-group").get(0);

    public SelenideElement hoverButton = $$(".btn-primary").findBy(text("Hover me faster!"));

    public SelenideElement waitButton = $$(".btn-primary").findBy(text("Wait for some time..."));

    public SelenideElement signIn = $(".card-footer img");

    public LoginPage open() {
        Selenide.open(Configuration.baseUrl + "/index.html");
        return this;
    }
}
