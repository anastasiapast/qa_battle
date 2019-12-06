package com.propellerads.qa.tests.profile;

import com.codeborne.selenide.Condition;
import com.propellerads.qa.pages.profile.ProfilePage;
import com.propellerads.qa.tests.base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProfileTest extends BaseTest {

    ProfilePage profilePage = new ProfilePage();

    @BeforeEach
    void init() {
        setAuthCookies();
    }

    @DisplayName("Set first and last name and Save")
    @Test
    void saveFirstAndLastNames(){
        profilePage.open();
        profilePage.setFirstName("John");
        profilePage.setLastName("Dow");
        profilePage.getSaveUserInfoBtn()
                .shouldBe(Condition.visible.because("'Save user info' button is not visible"))
                .click();
        profilePage.getSuccessUserInfoSaveInfo().waitUntil(Condition.visible.because("'User info successfully saved' alert doesn't appear"), 3000);

    }
}
