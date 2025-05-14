package kz.Akseleu;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
   private final SelenideElement loginInput = $x("//input[@type='text']");
   private final SelenideElement passwordInput = $x("//input[@type='password']");

   public MainPage(String url) {
       Selenide.open(url);
   }

   public void authorization(String text, String password){
       loginInput.setValue(text);
       passwordInput.setValue(password);
       passwordInput.sendKeys(Keys.ENTER);
   }

}
