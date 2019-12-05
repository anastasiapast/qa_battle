package com.propellerads.qa.tests.profile;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.propellerads.qa.pages.profile.ProfilePage;
import com.propellerads.qa.tests.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.propellerads.qa.WebUtils.correctPassword;
import static com.propellerads.qa.WebUtils.correctUser;

public class ProfileTest extends BaseTest {

    ProfilePage profilePage = new ProfilePage();

    @DisplayName("Set first and last name and Save")
    @Test
    void checkCorrectCredentials(){
        profilePage.open();
        profilePage.setFirstName("John");
        profilePage.setLastName("Dow");
        profilePage.getSaveUserInfoBtn()
                .shouldBe(Condition.visible.because("'Save user info' button is not visible"))
                .click();
    }
}
