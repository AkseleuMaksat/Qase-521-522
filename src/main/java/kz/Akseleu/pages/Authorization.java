package kz.Akseleu.pages;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class Authorization {
   private final SelenideElement loginInput = $x("//input[@type='text']");
   private final SelenideElement passwordInput = $x("//input[@type='password']");
   private final SelenideElement buttonProducts = $x("//li[(@class='ng-star-inserted')]/a[contains(., 'Товары')]");
   private final SelenideElement buttonRegistration = $x("//a[contains(., 'Списание')]");

   public Authorization(String url) {
       Selenide.open(url);
   }

   public void authorization(String text, String password){
       loginInput.setValue(text);
       passwordInput.setValue(password);
       passwordInput.sendKeys(Keys.ENTER);
   }
    public void openProductsPage() {
        buttonProducts.shouldBe(visible, Duration.ofSeconds(10)).click();
        buttonRegistration.shouldBe(visible).click();
    }

}
