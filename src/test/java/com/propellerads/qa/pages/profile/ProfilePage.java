package com.propellerads.qa.pages.profile;

import com.codeborne.selenide.*;
import com.propellerads.qa.pages.base.BasePage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.Keys;

import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Getter
public class ProfilePage extends BasePage {

    private ElementsCollection navLinks = $$(".nav-link");

    private SelenideElement firstNameInput = $("#firstNameInput");

    private SelenideElement lastNameInput = $("#lastNameInput");

    private SelenideElement saveUserInfoBtn = $$("button").findBy(text("Save user info"));

    private SelenideElement cardNumberInput = $("#cardNumberInput");

    private SelenideElement paymentSystemSelect = $("#paymentSystemSelect");

    private SelenideElement paymentRange = $("#paymentRange");

    private SelenideElement currentDayOfPayment = $("h6");

    private SelenideElement savePaymentInfoBtn = $$("button").findBy(text("Save payment info"));

    private SelenideElement successUserInfoSaveInfo = $("#successUserInfoSaveInfo");

    private SelenideElement successPaymentInfoSaveInfo = $("#successPaymentInfoSaveInfo");

    private ElementsCollection errorMessages = $$(".invalid-feedback");

    public String profileUrl = Configuration.baseUrl + "/profile.html";

    public List<String> paymentOptions = Arrays.asList(
            "Visa",
            "MasterCard",
            "Apple Card"
    );

    @Step("Open Profile page")
    public ProfilePage open() {
        Selenide.open(profileUrl);
        return this;
    }

    @Step("Open 'User Profile' navigation link")
    public ProfilePage goToUserProfile() {
        navLinks.findBy(text("User Profile"))
                .shouldBe(visible.because("'User Profile' navigation item is not visible")).click();
        return this;
    }

    @Step("Open 'Payment info' navigation link")
    public ProfilePage goToPaymentInfo() {
        navLinks.findBy(text("Payment info"))
                .shouldBe(visible.because("'Payment info' navigation item is not visible")).click();
        return this;
    }

    @Step("Set first name = {0}")
    public ProfilePage setFirstName(String firstName){
        setField(firstNameInput, firstName);
        return this;
    }

    @Step("Set last name = {0}")
    public ProfilePage setLastName(String lastName){
        setField(lastNameInput, lastName);
        return this;
    }

    @Step("Set card number = {0}")
    public ProfilePage setCardNumber(String cardNumber){
        setField(cardNumberInput, cardNumber);
        return this;
    }

    @Step("Check error message for first name = {0}; last name = {1}")
    public ProfilePage checkNamesErrorMessage(String firstName, String lastName){
        if (firstName.isEmpty()){
            errorMessages.find(text("Please set your first name"))
                    .shouldBe(visible.because("Error for empty First name is not visible"));
        }
        if (lastName.isEmpty()){
            errorMessages.find(text("Please set your last name"))
                    .shouldBe(visible.because("Error for empty Last name is not visible"));
        }
        return this;
    }

    @Step("Set day of payment = {0}")
    public ProfilePage setDayOfPayment(int day){
        paymentRange.shouldBe(Condition.visible.because("Day of payment range is not visible")).click();
        while (!currentDayOfPayment.text().equals("Current value: 1")){
            paymentRange.sendKeys(Keys.ARROW_LEFT);
        }
        for (int i = 0; i < day - 1; i++){
            paymentRange.sendKeys(Keys.ARROW_RIGHT);
        }
        return this;
    }

}
