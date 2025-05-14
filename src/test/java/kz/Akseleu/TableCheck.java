package kz.Akseleu;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TableCheck {

    private static final String staticStartDate = "01.05.2025";
    private static final String staticEndDate = "31.05.2025";
    Map<String, Integer> headerMap = getHeaderIndexMap();

    ElementsCollection rows = $$x("//tbody//tr[contains(@class,'p-selectable-row')]");

    Integer statusIndex = headerMap.get("Статус документа");
    Integer nameIndex = headerMap.get("Пользователь");
    Integer dateIndex = headerMap.get("Дата");
    public void checkDate(){
        loaderDisappear();
        LocalDate startDate = LocalDate.parse(staticStartDate, UmagTest.FORMATTER);
        LocalDate endDate = LocalDate.parse(staticEndDate, UmagTest.FORMATTER);
        for (SelenideElement row : rows) {
            row.shouldBe(visible, Duration.ofSeconds(10));
            ElementsCollection rowElement = row.$$x(".//td");
            String dateStr = rowElement.get(dateIndex).getText().trim();
            String dateOnly = dateStr.split("\\|")[0].trim();

            LocalDate localDate = LocalDate.parse(dateOnly, UmagTest.FORMATTER);
            assertTrue(
                    (localDate.isEqual(startDate) || localDate.isAfter(startDate)) &&
                            (localDate.isEqual(endDate) || localDate.isBefore(endDate)),
                    "Дата " + localDate + " должна быть между " + startDate + " и " + endDate
            );
        }
    }
    public void checkStatus(){
        loaderDisappear();
        for (SelenideElement row : rows) {
            row.shouldBe(visible, Duration.ofSeconds(10));
            ElementsCollection rowElement = row.$$x(".//td");

            String status = rowElement.get(statusIndex).getText().trim();
            assertTrue(
                    status.equals("Проведен") || status.equals("Удален"),
                    "Статус должен быть 'Проведен' или 'Удален', но был: " + status
            );
        }
    }
    public void checkName(){
        loaderDisappear();
        for (SelenideElement row : rows) {
            row.shouldBe(visible, Duration.ofSeconds(10));
            ElementsCollection rowElement = row.$$x(".//td");

            String name = rowElement.get(nameIndex).getText().trim();
            assertTrue(
                    name.equals("Миша"),
                    "Имя должно быть 'Миша', но было: " + name
            );
        }
    }
    private Map<String, Integer> getHeaderIndexMap() {
        loaderDisappear();
        ElementsCollection headers = $$x("//thead//tr//th");
        Map<String, Integer> headerIndexMap = new HashMap<>();

        for (int i = 0; i < headers.size(); i++) {
            String headerText = headers.get(i).getText().trim();
            headerIndexMap.put(headerText, i);
        }
        return headerIndexMap;
    }
    private void loaderDisappear() {
        $x("//div[contains(@class, 'loader')]").shouldBe(disappear, Duration.ofSeconds(60));
    }
}
