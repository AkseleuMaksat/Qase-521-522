package kz.Akseleu;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationPage {
    private final SelenideElement buttonFilter = $x("//app-umag-button-popover//khan-button//button");
    private final SelenideElement userDropdown = $x("//label[text()='Пользователи']/..//p-dropdown");
    private final SelenideElement buttonDropdown = $x("//label[text()='Статус документа']/..//khan-multiselect");
    private final ElementsCollection user = userDropdown.$$x(".//li");
    private final SelenideElement summit = $x("//span[text()='Применить']");
    private final SelenideElement lang = $("app-language-selector");
    private final SelenideElement clearFilter = $x("//span[contains(text(), 'Очистить')]/..");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final SelenideElement buttonDownload = $x("//span[text()='Экспорт']/..");
    private final SelenideElement buttonDownloadDiv = $x("//p-tieredmenusub//li");

    public void documentStatus() {
            lang.doubleClick();
            buttonFilter.shouldBe(visible, Duration.ofSeconds(10)).click();
            clearFilter();
            buttonFilter.shouldBe(visible, Duration.ofSeconds(10)).click();
            buttonDropdown.click();
            selectFromDropdown(Status.DELETED.getStatus());
            selectFromDropdown(Status.POSTED.getStatus());
            selectFromDropdown(Status.DRAFT.getStatus());
    }
    public void selectUser() {
        userDropdown.shouldBe(visible).click();
        user.get(0).shouldBe(visible).click();
        summit.shouldBe(visible).click();

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
