package com.propellerads.qa.pages.profile;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.propellerads.qa.pages.main.MainPage;
import lombok.Getter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Getter
public class ProfilePage {

    private ElementsCollection navLinks = $$(".nav-link");

    private SelenideElement firstNameInput = $("#firstNameInput");

    private SelenideElement lastNameInput = $("#lastNameInput");

    private SelenideElement saveUserInfoBtn = $$("button").findBy(text("Save user info"));

    private SelenideElement cardNumberInput = $("#cardNumberInput");

    private SelenideElement paymentSystemSelect = $("#paymentSystemSelect");

    private SelenideElement paymentRange = $("#paymentRange");

    private SelenideElement savePaymentInfoBtn = $$("button").findBy(text("Save payment info"));

    private SelenideElement successUserInfoSaveInfo = $("#successUserInfoSaveInfo");

    public String profileUrl = Configuration.baseUrl + "/profile.html";

    public ProfilePage open() {
        Selenide.open(profileUrl);
        return this;
    }

    public ProfilePage setFirstName(String firstName){
        firstNameInput.click();
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
        return this;
    }

    public ProfilePage setLastName(String firstName){
        lastNameInput.click();
        lastNameInput.clear();
        lastNameInput.sendKeys(firstName);
        return this;
    }
}
