package com.propellerads.qa.tests.main;

import com.codeborne.selenide.Condition;
import com.propellerads.qa.pages.main.MainPage;
import com.propellerads.qa.tests.base.BaseTest;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Feature("Main page tests")
public class MainTest extends BaseTest {

    MainPage mainPage = new MainPage();

    @BeforeEach
    void init() {
        setAuthCookies();
        mainPage.open();
    }

    @DisplayName("Check user types")
    @Test
    void checkNavigationItems(){
        mainPage.checkUserTypes();
    }


    private static Stream<Arguments> articlesProvider() {
        return Stream.of(
                Arguments.of("Advertisers", "Test Advertiser", 7000),
                Arguments.of("Advertisers", "Adidas", 7000),
                Arguments.of("Publishers", "Youtube", 5000),
                Arguments.of("Publishers", "Instagram", 12000),
                Arguments.of("Top level clients", "Jon Snow", 5000),
                Arguments.of("Top level clients", "Artur Fleck", 5000),
                Arguments.of("Top level clients", "Tim Cook", 10000),
                Arguments.of("Top level clients", "Bugs Bunny", 10000),
                Arguments.of("Top level clients", "Sasha Grey", 7000),
                Arguments.of("Top level clients", "You", 5000),
                Arguments.of("Top level clients", "Leonel Messi", 5000),
                Arguments.of("Top level clients", "Tony Stark", 10000),
                Arguments.of("Top level clients", "Elon Musk", 7000),
                Arguments.of("Top level clients", "Darth Vader", 5000)
        );
    }

    @ParameterizedTest(name = "Check article {1} visibility and downloading")
    @MethodSource("articlesProvider")
    void checkAdvertiserArticles(String type, String article, long waiter){
        mainPage.selectUserType(type);
        mainPage.selectUser(type, article);
        mainPage.checkArticle(article, waiter);
        mainPage.downloadArticle(article);
        mainPage.checkMoveToSavedAvailability();
        mainPage.checkImageSlider(waiter);
    }

    private static Stream<Arguments> oneArticlePerGroupProvider() {
        return Stream.of(
                Arguments.of("Advertisers", "Test Advertiser", 7000),
                Arguments.of("Publishers", "Youtube", 5000),
                Arguments.of("Top level clients", "You", 5000)
        );
    }


    @ParameterizedTest(name = "Check Move/Remove from saved for {1}")
    @MethodSource("oneArticlePerGroupProvider")
    void checkMoveRemoveOneArticle(String type, String article, long waiter){
        mainPage.selectUserType(type);
        mainPage.selectUser(type, article);
        mainPage.checkArticle(article, waiter);
        mainPage.checkMoveToSavedAvailability();
        mainPage.getMoveToSavedBtn()
                .shouldBe(Condition.visible.because("'Move to save' button is not visible"))
                .click();
        mainPage.checkUsersAreInSavedArticles(type, article);
        mainPage.getRemoveFromSavedBtn()
                .shouldBe(Condition.visible.because("'Remove from saved' button is not visible"))
                .click();
        mainPage.checkUsersAreInArticlesToRead(type, article);
    }

    @DisplayName("Check Move/Remove for Publishers group")
    @Test
    void checkMoveRemoveGroupArticle(){
        String type = "Publishers";
        mainPage.selectUserType(type);
        Map<String, Long> usersMap  = new HashMap<String, Long>() {{
            put("Youtube", 5000L);
            put("Instagram", 12000L);
        }};
        for (Map.Entry<String, Long> user : usersMap.entrySet()){
            mainPage.selectUser(type, user.getKey());
            mainPage.checkArticle(user.getKey(), user.getValue());
            mainPage.checkMoveToSavedAvailability();
            mainPage.getMoveToSavedBtn()
                    .shouldBe(Condition.visible.because("'Move to save' button is not visible"))
                    .click();
            mainPage.checkUsersAreInSavedArticles(type, user.getKey());
            mainPage.getRemoveFromSavedBtn()
                    .shouldBe(Condition.visible.because("'Remove from saved' button is not visible"))
                    .click();
            mainPage.checkUsersAreInArticlesToRead(type, user.getKey());
        }
    }


    @DisplayName("Move to saved should be disabled after pressing")
    @Test
    void checkMoveToSavedAfterPressing(){
        mainPage.selectUserType("Top level clients");
        mainPage.selectUser("Top level clients", "Artur Fleck");
        mainPage.checkArticle("Artur Fleck", 5000);
        mainPage.checkMoveToSavedAvailability();
        mainPage.getMoveToSavedBtn()
                .shouldBe(Condition.visible.because("'Move to save' button is not visible"))
                .click();
        mainPage.getMoveToSavedBtn()
                .shouldBe(Condition.disabled.because("'Move to save' button should be disabled after pressing"));
    }

    @DisplayName("Remove from saved should be disabled after pressing")
    @Test
    void checkRemoveFromSavedAfterPressing(){
        mainPage.selectUserType("Top level clients");
        mainPage.selectUser("Top level clients", "Jon Snow");
        mainPage.checkArticle("Jon Snow", 5000);
        mainPage.checkMoveToSavedAvailability();
        mainPage.getMoveToSavedBtn()
                .shouldBe(Condition.visible.because("'Move to save' button is not visible"))
                .click();
        mainPage.getRemoveFromSavedBtn()
                .shouldBe(Condition.visible.because("'Remove from saved' button is not visible"))
                .click();
        mainPage.getRemoveFromSavedBtn()
                .shouldBe(Condition.disabled.because("'Remove from saved' button should be disabled after pressing"));

    }

}
