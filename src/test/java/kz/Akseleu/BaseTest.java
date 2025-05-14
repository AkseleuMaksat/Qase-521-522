package kz.Akseleu;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.closeWebDriver;

abstract public class BaseTest {
    @BeforeEach
    public void init(){
        Configuration.pageLoadTimeout = 90000;
        Selenide.open();
    }
    @AfterEach
    public void after() {
        closeWebDriver();
    }
}
