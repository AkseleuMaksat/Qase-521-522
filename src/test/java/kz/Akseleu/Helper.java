package kz.Akseleu;

import com.codeborne.selenide.ElementsCollection;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class Helper {
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
}
