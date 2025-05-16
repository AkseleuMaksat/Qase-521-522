package kz.Akseleu;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import kz.Akseleu.constants.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;

abstract public class BaseTest {
    @BeforeEach
    public void init() {
        Configuration.pageLoadTimeout = 90000;
        Configuration.baseUrl = Constants.BASE_URL;
    }

    @AfterEach
    public void after() {
        closeWebDriver();
    }
}
