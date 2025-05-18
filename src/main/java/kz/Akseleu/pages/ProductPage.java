package kz.Akseleu.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import kz.Akseleu.constants.Constants;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class ProductPage {
    private final ElementsCollection tableRow = $$x("//app-new-table-common//tbody//tr");
    private final SelenideElement searchLine = $x("//app-search-input//input");
    private final SelenideElement closeButton = $x("(//khan-button//span[contains(text(), 'Закрыть')])[1]/..");
    private final SelenideElement yesButtonForLogger = $x("//p-dynamicdialog//app-confirm//khan-button[2]");

    public void open(String productId) {
        Selenide.open("/store/0/decom/"+productId+"/view");
        $x("//app-new-table-common").shouldBe(exist, Duration.ofSeconds(10));
    }
    public ElementsCollection getTableRow() {
        return tableRow;
    }
    public void setSearchText(){
        searchLine.setValue(Constants.BARCODE);
        searchLine.sendKeys(Keys.ENTER);
    }
    public String checkSearchLine(){
        return searchLine.getValue();
    }
    public void clickCloseButton(){
        closeButton.click();

    }
    public boolean checkDynamicLogger(){
        return $x("//p-dynamicdialog").exists();
    }
    public void clickYesButtonForLogger(Boolean booleanExpression){
        if(booleanExpression){
            yesButtonForLogger.click();
        }
    }
    public void inputShouldNotExist(){
        searchLine.shouldNotBe(visible);
    }

}
