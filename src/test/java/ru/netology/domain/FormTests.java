package ru.netology.domain;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;



public class FormTests {
    @BeforeEach
    public void setUp() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");

    }

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(6);


    @Test
    public void shouldSendForm() {
        $x("//input[@type= 'text']").val("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type= 'tel']").val(planningDate);
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79265421458");
        $("[data-test-id=agreement]").click();
        $$(withText("Забронировать")).first().click();
        $("[class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(14));

    }

    @Test
    public void shouldIncorrectCity() {
        $x("//input[@type= 'text']").val("Молоково");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type= 'tel']").val(planningDate);
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79265421458");
        $("[data-test-id=agreement]").click();
        $$(withText("Забронировать")).first().click();
        $("[data-test-id = 'city'] .input__sub")
                .shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
        public void shouldIncorrectNumber() {
            $x("//input[@type= 'text']").val("Москва");
            $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            $x("//input[@type= 'tel']").val(planningDate);
            $("[data-test-id=name] input").setValue("Иван Петров");
            $("[data-test-id=phone] input").setValue("+7926542145");
            $("[data-test-id=agreement]").click();
            $$(withText("Забронировать")).first().click();
            $("[data-test-id = 'phone'] .input__sub")
                    .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldIncorrectName() {
        $x("//input[@type= 'text']").val("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type= 'tel']").val(planningDate);
        $("[data-test-id=name] input").setValue("Ivan Petrov");
        $("[data-test-id=phone] input").setValue("+79265421453");
        $("[data-test-id=agreement]").click();
        $$(withText("Забронировать")).first().click();
        $("[data-test-id = 'name'] .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    public void shouldNotAgreement() {
        $x("//input[@type= 'text']").val("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type= 'tel']").val(planningDate);
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79265421453");
        $$(withText("Забронировать")).first().click();
        $("[data-test-id=agreement]")
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
    @Test
    public void shouldEmptyCity() {
        $x("//input[@type= 'text']").val("");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type= 'tel']").val(planningDate);
        $("[data-test-id=name] input").setValue("Иван Петров");
        $("[data-test-id=phone] input").setValue("+79265421453");
        $("[data-test-id=agreement]").click();
        $$(withText("Забронировать")).first().click();
        $("[data-test-id = 'city'] .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }
}



