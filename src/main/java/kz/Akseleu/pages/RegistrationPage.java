package kz.Akseleu.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import kz.Akseleu.enam.Status;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationPage {
    private final SelenideElement buttonFilter = $x("//app-umag-button-popover//khan-button//button");
    private final SelenideElement buttonMonth = $x("//span[text()=' Месяц ']");
    private final SelenideElement userDropdown = $x("//label[text()='Пользователи']/..//p-dropdown");
    private final SelenideElement buttonDropdown = $x("//label[text()='Статус документа']/..//khan-multiselect");

    private final ElementsCollection user = userDropdown.$$x(".//li");
    private final SelenideElement buttonSummit = $x("//span[text()='Применить']");
    private final SelenideElement lang = $("app-language-selector");
    private final SelenideElement clearFilter = $x("//span[contains(text(), 'Очистить')]/..");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public void deletedCheckBox() {
        selectFromDropdown(Status.DELETED.getStatus());
    }
    public void selectUser(String username) {
        for (int i = 0; i < user.size(); i++) {
            if (username.equals(user.get(i).text())) {
                user.get(i).shouldBe(visible).click();
                return;
            }
        }
    }
    public void userDropdown() {
        userDropdown.shouldBe(visible).click();
    }
    public void buttonSummit() {
        buttonSummit.shouldBe(visible).click();
    }

    public void filterButton(){
        lang.doubleClick();
        buttonFilter.shouldBe(visible, Duration.ofSeconds(10)).click();
        clearFilter();
        buttonFilter.shouldBe(visible, Duration.ofSeconds(10)).click();
    }
    public void monthButton() {
        buttonMonth.shouldBe(visible, Duration.ofSeconds(10)).click();
    }
    public void statusDocument(){
        buttonDropdown.shouldBe(visible, Duration.ofSeconds(10)).click();
    }

    private void clearFilter() {
        clearFilter.click();
    }
    private void selectFromDropdown(String valueToSelect) {
        SelenideElement listItem = $x("//khan-dropdown-list-item//label[contains(text(), '" + valueToSelect + "')]")
                .shouldBe(Condition.visible);
        listItem.click();
    }
}
