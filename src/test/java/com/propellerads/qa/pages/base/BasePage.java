package com.propellerads.qa.pages.base;

import com.codeborne.selenide.SelenideElement;

public class BasePage {

    protected void setField(SelenideElement element, String value){
        element.click();
        element.clear();
        element.sendKeys(value);
    }
}
