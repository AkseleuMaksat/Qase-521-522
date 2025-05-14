package kz.Akseleu;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class SecondPage {
    private final SelenideElement buttonProducts = $x("//li[(@class='ng-star-inserted')]/a[contains(., 'Товары')]");
    private final SelenideElement buttonRegistration = $x("//a[contains(., 'Списание')]");

    public void openProductsPage() {
        Allure.step("Открытие страницы списание", () -> {
            buttonProducts.shouldBe(visible, Duration.ofSeconds(10)).click();
            buttonRegistration.shouldBe(visible).click();
        });
    }

 }