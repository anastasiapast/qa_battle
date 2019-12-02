package com.propellerads.qa.tests.login;

import com.propellerads.qa.pages.login.LoginPage;
import com.propellerads.qa.tests.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest {

    LoginPage loginPage = new LoginPage();

    @DisplayName("")
    @Test
    void correctLogin(){
        loginPage.open();
        loginPage.formInput.click();
        loginPage.loginInput.click();
        loginPage.loginInput.setValue("test");
        loginPage.passwordInput.setValue("test");

        //https://medium.com/@rosolko/fast-authorization-level-local-storage-6c84e9b3cef1
        //https://medium.com/@rosolko/boost-you-autotests-with-fast-authorization-b3eee52ecc19
        //https://medium.com/@rosolko/simple-allure-2-configuration-for-gradle-8cd3810658dd
    }


}
