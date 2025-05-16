package kz.Akseleu.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import kz.Akseleu.constants.Constants;
import kz.Akseleu.enums.Status;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class DecommissionPage {
    private final SelenideElement buttonFilter = $x("//app-umag-button-popover//khan-button//button");
    private final SelenideElement buttonMonth = $x("//span[text()=' Месяц ']");
    private final SelenideElement userDropdown = $x("//label[text()='Пользователи']/..//p-dropdown");
    private final SelenideElement buttonDropdown = $x("//label[text()='Статус документа']/..//khan-multiselect");

    private final ElementsCollection user = userDropdown.$$x(".//li");
    private final SelenideElement buttonSummit = $x("//span[text()='Применить']");
    private final SelenideElement lang = $("app-language-selector");
    private final SelenideElement clearFilter = $x("//span[contains(text(), 'Очистить')]/..");
    private final SelenideElement textUserBox = $x("//input[@role ='searchbox']");
    private final SelenideElement startDayBox = $x("(//input[@role='combobox'])[1]");
    private final SelenideElement endDayBox = $x("(//input[@role='combobox'])[2]");

    private final ElementsCollection tableRows = $$x("//tbody//tr[contains(@class,'p-selectable-row')]");

    private final SelenideElement statusDocumentDiv = $x("//khan-dropdown-ui-decorator//div//div//div");
    private final DateTimeFormatter formatterDayMonth = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    public ElementsCollection getTableRows() {
        return tableRows;
    }

    public void open() {
        Selenide.open("/store/0/decom");
    }

    public void selectFirstUser() {
        user.get(0).shouldBe(visible).click();
    }

//    public void selectUser(String username) {
//        for (int i = 0; i < user.size(); i++) {
//            if (username.equals(user.get(i).text())) {
//                user.get(i).shouldBe(visible).click();
//                return;
//            }
//        }
//    }

    public void filterPanelExist() {
        $x("//app-panel-content").shouldBe(exist);
    }

    public void setValueUser(String username) {
        textUserBox.setValue(username);
    }

    public void openUserDropdown() {
        userDropdown.shouldBe(visible).click();
    }
    public void clickSummit() {
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
    public LocalDate startDay() {
        return LocalDate.parse(startDayBox.getValue() + "-" + Constants.NOW.getYear(), formatterDayMonth);
    }
    public LocalDate endDay() {
        return LocalDate.parse(endDayBox.getValue() + "-" + Constants.NOW.getYear(), formatterDayMonth);
    }

    public LocalDate today(){
        return LocalDate.now();
    }
    public void statusDocument(){
        buttonDropdown.shouldBe(visible, Duration.ofSeconds(10)).click();
    }

    private void clearFilter() {
        clearFilter.click();
    }

    public void selectFromDropdown(Status status) {
        SelenideElement listItem = $x("//khan-dropdown-list-item//label[contains(text(), '" + status.get() + "')]")
                .shouldBe(Condition.visible);
        listItem.click();
    }

    public String textDropdown() {
        return statusDocumentDiv.getText();
    }
    public List<String> dropDownList() {
        return $$x("//khan-dropdown-list-item//li").texts();
    }
    public String userNameFormated(){
        String userFilter = $x("//khan-filter-chips//span[contains(text(), 'Пользователь:')]").getText().trim();
        return userFilter.replace("Пользователь: ", "");
    }
    public String dateFormated(){
        return  $x("//khan-filter-chips//span[contains(text(), 'по')]").getText().trim();
    }
    public String statusFormated(){
        String statusFilter = $x("//khan-filter-chips//span[contains(text(), 'Статус документа')]").getText().trim();
        return statusFilter.replace("Статус документа: ", "");
    }

}
