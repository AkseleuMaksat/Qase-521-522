package kz.Akseleu.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$x;

public class AuthorizationPage {
    private final SelenideElement loginInput = $x("//input[@type='text']");
    private final SelenideElement passwordInput = $x("//input[@type='password']");

    public static AuthorizationPage open() {
        Selenide.open("/auth/login");
        return new AuthorizationPage();
    }

    public void authorization(String text, String password) {
        loginInput.setValue(text);
        passwordInput.setValue(password);
        passwordInput.sendKeys(Keys.ENTER);
        $x("//app-supply-overview").shouldBe(exist, Duration.ofSeconds(10));
    }
}
