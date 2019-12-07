package com.propellerads.qa.pages.main;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.CollectionCondition.textsInAnyOrder;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

@Getter
public class MainPage {

    private ElementsCollection userTypes = $$(".tree-main-node");

    private SelenideElement cardTitle = $(".card-title");

    private SelenideElement cardText = $(".card-text");

    private SelenideElement articleText = $("textarea");

    private SelenideElement downloadBtn = $$("button").findBy(text("Download info"));

    private SelenideElement articleImage = $("#heroImage");

    private SelenideElement slider = $(".ui-slider-handle");

    private SelenideElement moveToSavedBtn = $$("button").findBy(text("Move to saved"));

    private SelenideElement removeFromSavedBtn = $$("button").findBy(text("Removed from saved"));

    private ElementsCollection savedUserNames = $(".right-sub-tree").$$(".sub-tree-element");

    private ElementsCollection savedUserTypes = $(".text-right").$$(".tree-main-node");

    public ElementsCollection userNames(SelenideElement userType){
        return userType.$$(".sub-tree-element");
    }

    public String mainUrl = Configuration.baseUrl + "/main.html";

    Map<String, Integer> userTypesMap  = new HashMap<String, Integer>() {{
        put("Advertisers", 2);
        put("Publishers", 2);
        put("Top level clients", 10);
    }};

    public MainPage open() {
        Selenide.open(mainUrl);
        return this;
    }

    @Step("Check user types")
    public MainPage checkUserTypes(){
        assertEquals(3, userTypes.size(), "Count of user types should be equal to 3");
        List<String> keys = new ArrayList<>(userTypesMap.keySet());
        userTypes.shouldHave(textsInAnyOrder(keys).because("User types should be " + userTypesMap.keySet()));
        for (Map.Entry<String, Integer> userType : userTypesMap.entrySet()) {
            selectUserType(userType.getKey());
            assertEquals(userType.getValue(), userNames(userTypes.find(text(userType.getKey()))).size(), "Count of articles for " + userType.getKey() + " is not equal to " + userType.getValue());
        }
        return this;
    }

    @Step("Select user type - {0}")
    public MainPage selectUserType(String userType){
        userTypes.find(text(userType))
                .shouldBe(visible.because(userType + " is not visible"))
                .click();
        return this;
    }

    @Step("Select user - {0}")
    public MainPage selectUser(String userType, String user){
        userNames(userTypes.find(text(userType))).find(text(user))
                .scrollTo()
                .shouldBe(visible.because(user + " is not visible"))
                .click();
        return this;
    }

    @Step("Check user - {1} - is in Articles to read")
    public MainPage checkUsersAreInArticlesToRead(String type, String user){
        assertFalse(savedUserTypes.texts().contains(user), user + " is still in Saved article");
        assertTrue(userNames(userTypes.find(text(type))).texts().contains(user), user + " is not in Article to read");
        return this;
    }

    @Step("Check users - {1} - is in Saved articles")
    public MainPage checkUsersAreInSavedArticles(String type, String user){
        savedUserTypes.find(text(type))
                .shouldBe(visible.because(type + " article type is not in Saved articles"))
                .click();
        assertTrue(savedUserNames.texts().contains(user), user + " is not in Saved article");
        assertFalse(userNames(userTypes.find(text(type))).texts().contains(user), user + " is still in Article to read");
        return this;
    }

    @Step("Check article fields - {0}")
    public MainPage checkArticle(String article, long waiter){
        cardTitle.waitUntil(visible.because(article + " article is not visible after " + waiter + " seconds"), waiter);
        articleImage.shouldBe(visible.because("Image is not visible"));
        slider.shouldBe(visible.because("Image slider is not visible"));
        assertEquals(article, cardTitle.text(), "Title of article is wrong");
        if (!article.equals("You"))
            assertFalse(cardText.text().isEmpty(), "Article description is empty");
        assertFalse(articleText.text().isEmpty(), "Article text is empty");
        return this;
    }

    @Step("Check image slider")
    public MainPage checkImageSlider(long waiter){
        articleImage.waitUntil(visible.because("Image is not visible after " + waiter + " seconds"), waiter);
        slider.scrollTo().shouldBe(visible.because("Image slider is not visible"))
                .click();
        int width = Integer.parseInt(articleImage.getAttribute("width"));
        for (int i = 0; i < 10; i++){
            slider.sendKeys(Keys.ARROW_RIGHT);
        }
        assertEquals(width + 10, Integer.parseInt(articleImage.getAttribute("width")), "Width was not changed");
        assertEquals(Integer.parseInt(articleImage.getAttribute("width")), Integer.parseInt(articleImage.getAttribute("height")), "Width is not equal to height");
        return this;
    }

    @Step("Check 'Move to saved' button availability")
    public MainPage checkMoveToSavedAvailability(){
        moveToSavedBtn.hover()
                .shouldBe(visible.because("'Move to saved' button should be visible"))
                .shouldBe(disabled.because("'Move to save' button should be disabled before reading a whole article"));
        articleText.click();
        int scrollHeight = Integer.parseInt(articleText.getAttribute("scrollHeight"));
        Selenide.executeJavaScript("arguments[0].scrollTop = "+ scrollHeight +";", articleText);
        moveToSavedBtn.shouldBe(enabled.because("'Move to save' button should be enabled after reading a whole article"));
        return this;
    }

    @Step("Check downloaded article - {0}")
    public MainPage downloadArticle(String article) {
        String expectedText = articleText.text().trim();
        downloadBtn.shouldBe(visible.because("'Download info' button is not visible"));
        File file = null;
        Path filePath = null;
        try {
            try {
                file = downloadBtn.download(); //don't know why it doesn't work :(
            } catch (FileNotFoundException e) {
                String exception = e.getMessage();
                String url = exception.substring(exception.indexOf("http"), exception.indexOf("->")).trim();
                file = download(url);
            }
            filePath = file.toPath();
            assertTrue(file.exists(), "File was not downloaded");
            String content = Files.readString(filePath, StandardCharsets.US_ASCII).replace(" \n", "\n").trim();
            assertEquals(expectedText, content, "Downloaded file content is not equal to textarea content");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }


}
