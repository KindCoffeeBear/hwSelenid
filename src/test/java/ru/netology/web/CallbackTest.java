package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CallbackTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSentFormWithManualInput() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id='city'] input").setValue("Мурманск");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        form.$("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        form.$("[data-test-id='date'] input").setValue(planningDate);
        form.$("[data-test-id='name'] input").setValue("Иванов Василий");
        form.$("[data-test-id='phone'] input").setValue("+79994322334");
        form.$("[data-test-id='agreement']").click();
        form.$(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
    }

    @Test
    void shouldSentFormWithSelectionFromDropdownList() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id='city'] input").setValue("Кр");
        $$(".menu-item").find(exactText("Краснодар")).click();
        form.$("[data-test-id='date'] input").click();
        int planningDay = 7;
        String planningDate = generateDate(planningDay, "d");

        if (generateDate(3, "MM").equals(generateDate(planningDay, "MM"))) {
            $$(".calendar__day").find(exactText(planningDate)).click();
        } else {
            $(".calendar__arrow_direction_right[data-step='1']").click();
            $$(".calendar__day").find(exactText(planningDate)).click();
        }

        form.$("[data-test-id='name'] input").setValue("Иванов Василий");
        form.$("[data-test-id='phone'] input").setValue("+79994322334");
        form.$("[data-test-id='agreement']").click();
        form.$(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Успешно!"));
    }
}
