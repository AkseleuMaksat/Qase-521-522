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
    private final SelenideElement buttonWeek = $x("//span[text()=' Неделя ']");
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

//    private final SelenideElement quantityElement = $x("(//p-dropdown//div)[3]");
    //BARCODE
    private final SelenideElement barcodeInput = $x("//khan-text-input//input");
    private final SelenideElement firstProduct = $x("//tbody//tr[contains(@class,'p-selectable-row')][1]//td[2]");
    private final SelenideElement secondProduct = $x("//tbody//tr[contains(@class,'p-selectable-row')][2]//td[2]");
    private final SelenideElement lastProduct = $x("//tbody//tr[contains(@class,'p-selectable-row')][last()]//td[2]");

    public void open() {
        Selenide.open("/store/0/decom");
    }

    public ElementsCollection getTableRows() {
        return tableRows;
    }
    public void shouldBeTable(){
        $x("//app-overview").shouldBe(visible);
    }
    public void clickSummit() {
        buttonSummit.shouldBe(visible).click();
    }

    public void filterButton(){
        lang.doubleClick();
        buttonFilter.shouldBe(visible, Duration.ofSeconds(10)).click();
        clearFilter.click();
        buttonFilter.shouldBe(visible, Duration.ofSeconds(10)).click();
    }
    //--------------------about_USER--------------------//
    public void selectFirstUser() {
        user.get(0).shouldBe(visible).click();
    }

    public void filterPanelExist() {
        $x("//app-panel-content").shouldBe(exist);
    }

    public void setValueUser(String username) {
        textUserBox.setValue(username);
    }
//  public void selectUser(String username) {
//        for (int i = 0; i < user.size(); i++) {
//            if (username.equals(user.get(i).text())) {
//                user.get(i).shouldBe(visible).click();
//                return;
//            }
//        }
//    }
    public void openUserDropdown() {
        userDropdown.shouldBe(visible).click();
    }
    public String userNameFormated(){
        String userFilter = $x("//khan-filter-chips//span[contains(text(), 'Пользователь:')]").getText().trim();
        return userFilter.replace("Пользователь: ", "");
    }
    //--------------------about_USER--------------------//

    //--------------------about_DATE--------------------//
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
    public String dateFormated(){
        return  $x("//khan-filter-chips//span[contains(text(), 'по')]").getText().trim();
    }
    public void weekButton() {
        buttonWeek.shouldBe(visible, Duration.ofSeconds(10)).click();
    }
    //--------------------about_DATE--------------------//

    //----------about_Document_Status----------//
    public void selectFromDropdown(Status status) {
        SelenideElement listItem = $x("//khan-dropdown-list-item//label[contains(text(), '" + status.get() + "')]")
                .shouldBe(Condition.visible);
        listItem.click();
    }
    public String statusTextDropdown() {
        return statusDocumentDiv.getText();
    }
    public List<String> statusDropDownList() {
        return $$x("//khan-dropdown-list-item//li").texts();
    }
    public String statusFormated(){
        String statusFilter = $x("//khan-filter-chips//span[contains(text(), 'Статус документа')]").getText().trim();
        return statusFilter.replace("Статус документа: ", "");
    }
    //----------about_Document_Status----------//

    //--------------------about_BARCODE--------------------//
    public void clickBarcodeInput() {
        barcodeInput.click();
    }
    public void setBarcodeInput(String username) {
        barcodeInput.setValue(username);
    }
    public String barCodeText() {
        return barcodeInput.getValue();

    }
    public String barCodeFormated(){
        String barCodeChip = $x("//khan-filter-chips//span[contains(text(), 'Штрихкод и Название:')]").getText().trim();
        return barCodeChip.replace("Штрихкод и Название: ", "");
    }
    public void openFirstDocument(){
        firstProduct.shouldBe(visible).click();
    }
    public void openSecondProduct() {
        secondProduct.shouldBe(visible).click();
    }
    public void openLastProduct() {
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight);");
        lastProduct.shouldBe(visible).click();
    }
    private SelenideElement getProductByIndex(int index) {
        return $x(String.format("//tbody//tr[contains(@class,'p-selectable-row')][%d]//td[2]", index));
    }

    //--------------------about_BARCODE--------------------//



}
