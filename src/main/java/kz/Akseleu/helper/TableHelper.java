package kz.Akseleu.helper;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class TableHelper {
    public Map<String, Integer> getHeaderIndexMap() {
        loaderDisappear();
        ElementsCollection headers = $$x("//thead//tr//th");
        Map<String, Integer> headerIndexMap = new HashMap<>();

        for (int i = 0; i < headers.size(); i++) {
            String headerText = headers.get(i).getText().trim();
            headerIndexMap.put(headerText, i);
        }
        return headerIndexMap;
    }

    public void loaderDisappear() {
        $x("//div[contains(@class, 'loader')]").shouldBe(disappear, Duration.ofSeconds(60));
    }

    public List<String> getColumnData(ElementsCollection rows, String columnName) {
        loaderDisappear();
        Map<String, Integer> headerMap = getHeaderIndexMap();
        Integer dateIndex = headerMap.get(columnName);
        List<String> listOfTableData = new ArrayList<>();
        for (SelenideElement row : rows) {
            ElementsCollection rowElement = row.$$x(".//td");
            String tableData = rowElement.get(dateIndex).getText().trim();
            listOfTableData.add(tableData);
        }
        return listOfTableData;
    }
}
