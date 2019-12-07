package com.propellerads.qa.tests.profile;

import com.codeborne.selenide.Condition;
import com.propellerads.qa.pages.profile.ProfilePage;
import com.propellerads.qa.tests.base.BaseTest;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Profile page tests")
public class ProfileTest extends BaseTest {

    ProfilePage profilePage = new ProfilePage();

    @BeforeEach
    void init() {
        setAuthCookies();
        profilePage.open();
    }

    @DisplayName("Check navigation items")
    @Test
    void checkNavigationItems(){
        assertEquals(2, profilePage.getNavLinks().size(), "There should be 2 navigation items");
        profilePage.getNavLinks().shouldHave(texts("User Profile", "Payment info")
                .because("There should be User Profile and Payment info navigation items"));
    }

    private static Stream<Arguments> invalidUserNames() {
        return Stream.of(
                Arguments.of("John", ""),
                Arguments.of("", "Dow")
        );
    }

    @ParameterizedTest(name = "Check empty First and Last names")
    @MethodSource("invalidUserNames")
    void checkEmptyNames(String firstName, String lastName){
        profilePage.goToUserProfile();
        profilePage.setFirstName(firstName);
        profilePage.setLastName(lastName);
        profilePage.getSaveUserInfoBtn()
                .shouldBe(Condition.visible.because("'Save user info' button is not visible"))
                .click();
        profilePage.checkNamesErrorMessage(firstName, lastName);
    }

    private static Stream<Arguments> validUserNames() {
        return Stream.of(
                Arguments.of("John", "Snow"),
                Arguments.of("Q", "1")
        );
    }

    @ParameterizedTest(name = "Set First and Last name and Save")
    @MethodSource("validUserNames")
    void saveFirstAndLastNames(String firstName, String lastName){
        profilePage.goToUserProfile();
        profilePage.setFirstName(firstName);
        profilePage.setLastName(lastName);
        profilePage.getSaveUserInfoBtn()
                .shouldBe(Condition.visible.because("'Save user info' button is not visible"))
                .click();
        profilePage.getSuccessUserInfoSaveInfo().waitUntil(Condition.visible.because("'User info successfully saved' alert doesn't appear"), 3000);
        assertEquals(firstName, profilePage.getFirstNameInput().val(), "First name wasn't saved");
        assertEquals(lastName, profilePage.getLastNameInput().val(), "Last name wasn't saved");
    }

    @DisplayName("Set > 255 symbols to First and Last name fields and Save")
    @Test
    void setOneSymbolAsNames(){
        StringBuffer name = new StringBuffer();
        for (int i = 0; i < 256; i++) {
            name.append("A");
        }
        profilePage.goToUserProfile();
        profilePage.setFirstName(name.toString());
        profilePage.setLastName(name.toString());
        profilePage.getSaveUserInfoBtn()
                .shouldBe(Condition.visible.because("'Save user info' button is not visible"))
                .click();
        profilePage.getSuccessUserInfoSaveInfo().waitUntil(Condition.visible.because("'User info successfully saved' alert doesn't appear"), 3000);
        assertEquals(name.toString(), profilePage.getFirstNameInput().val(), "First name wasn't saved");
        assertEquals(name.toString(), profilePage.getLastNameInput().val(), "Last name wasn't saved");
    }


    @DisplayName("Set invalid Card number and Save")
    @ParameterizedTest
    @ValueSource(strings = {"##$@###", "string"})
    void setInvalidCardNumber(String cardNumber){
        String option = profilePage.paymentOptions.get(1);
        profilePage.goToPaymentInfo();
        profilePage.getPaymentSystemSelect().
                shouldBe(visible.because("Payment system options are not visible"))
                .selectOption(option);
        profilePage.setCardNumber(cardNumber);
        profilePage.getSavePaymentInfoBtn()
                .shouldBe(Condition.visible.because("'Save payment info' button is not visible"))
                .click();
        profilePage.getErrorMessages().find(text("Card number should contain only digits"))
                .shouldBe(visible.because("Error for invalid Card number is not visible"));
    }

    @DisplayName("Set empty Card number and Save")
    @Test
    void setEmptyCardNumber(){
        profilePage.goToPaymentInfo();
        profilePage.setCardNumber("");
        profilePage.getSavePaymentInfoBtn()
                .shouldBe(Condition.visible.because("'Save payment info' button is not visible"))
                .click();
        profilePage.getErrorMessages().find(text("Please set your card number"))
                .shouldBe(visible.because("Error for empty Card number is not visible"));
    }

    @DisplayName("Check payment system options")
    @Test
    void checkPaymentSystemOptions(){
        profilePage.goToPaymentInfo();
        profilePage.setCardNumber("12345");
        for (String option : profilePage.paymentOptions){
            profilePage.getPaymentSystemSelect().shouldBe(visible.because("Payment system options are not visible")).selectOption(option);
            profilePage.getSavePaymentInfoBtn()
                    .shouldBe(visible.because("'Save payment info' button is not visible"))
                    .click();
            profilePage.getSuccessPaymentInfoSaveInfo().waitUntil(Condition.visible.because("'Payment info successfully saved' alert doesn't appear"), 3000);
            assertEquals(option, profilePage.getPaymentSystemSelect().getSelectedText(),"Payment option is not saved");
        }
    }

    @DisplayName("Select empty payment system option")
    @Test
    void selectEmptyPaymentSystemOption(){
        profilePage.goToPaymentInfo();
        profilePage.setCardNumber("12345");
        profilePage.getPaymentSystemSelect().selectOption(0);
        profilePage.getSavePaymentInfoBtn()
                .shouldBe(Condition.visible.because("'Save payment info' button is not visible"))
                .click();
        profilePage.getErrorMessages().find(text("Please select your payment system"))
                .shouldBe(visible.because("Error for empty payment system is not visible"));
    }

    @DisplayName("Check day of payment range")
    @Test
    void checkDayOfPayment(){
        int day = (int)(Math.random()*((30 - 2)+ 1)) + 2;
        profilePage.goToPaymentInfo();
        profilePage.setDayOfPayment(day);
        assertEquals("Current value: " + day, profilePage.getCurrentDayOfPayment().text(),
                "Payment day is not saved");
    }


    @DisplayName("Set correct payment info")
    @Test
    void setCorrectPaymentInfo(){
        int day = (int)(Math.random()*((30 - 2)+ 1)) + 2;
        String option = profilePage.paymentOptions.get(0);
        String cardNumber = "1234 5678 9012 3456";
        profilePage.goToPaymentInfo();
        profilePage.setCardNumber(cardNumber);
        profilePage.getPaymentSystemSelect().
                shouldBe(visible.because("Payment system options are not visible"))
                .selectOption(option);
        profilePage.setDayOfPayment(day);
        profilePage.getSavePaymentInfoBtn()
                .shouldBe(Condition.visible.because("'Save payment info' button is not visible"))
                .click();
        profilePage.getSuccessPaymentInfoSaveInfo().waitUntil(Condition.visible.because("'Payment info successfully saved' alert doesn't appear"), 3000);
        assertEquals(option, profilePage.getPaymentSystemSelect().getSelectedText(),"Payment option is not saved");
        assertEquals("Current value: " + day, profilePage.getCurrentDayOfPayment().text(),
                "Payment day is not saved");
        assertEquals(cardNumber, profilePage.getCardNumberInput().val(),
                "Card number is not saved");
    }

}
