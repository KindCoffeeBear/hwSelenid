package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CallbackTest {
    @Test
    void shouldSentForm() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id='city'] input").setValue("Мурманск");
        form.$("[data-test-id='name'] input").setValue("Иванов Василий");
        form.$("[data-test-id='phone'] input").setValue("+79994322334");
        form.$("[data-test-id='agreement']").click();
        form.$(".button").click();
        $("[data-test-id='notification'] .notification__title").shouldBe(visible, Duration.ofSeconds(5));
    }
}
