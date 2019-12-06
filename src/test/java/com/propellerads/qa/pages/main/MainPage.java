package com.propellerads.qa.pages.main;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

    private ElementsCollection articleTypes = $$(".tree-main-button");

    private SelenideElement cardTitle = $(".card-title");

    private SelenideElement cardText = $(".card-text");

    private SelenideElement articleText = $("textarea");

    private SelenideElement downloadBtn = $("button[text='Download info']");

    private SelenideElement articleImage = $("#heroImage");

    private SelenideElement slider = $(".ui-slider-handle");

    private SelenideElement moveToSavedBtn = $("button[text='Move to saved']");

    private SelenideElement removeFromSavedBtn = $("button[text='Removed from saved']");


    public ElementsCollection articles(SelenideElement articleType){
        return articleType.$$(".sub-tree-element");
    }

    public String mainUrl = Configuration.baseUrl + "/index.html";

    public MainPage open() {
        Selenide.open(mainUrl);
        return this;
    }


}
