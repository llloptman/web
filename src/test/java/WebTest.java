import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

class WebTest {
    @Test
    void ShouldPass(){
        open("http://localhost:9999/");
        SelenideElement form = $("#root > div > form");
        form.$("[data-test-id='name'] input").setValue("Василий");
        form.$("[data-test-id='phone'] input").setValue("+71234567890");
        form.$(".checkbox__box").click();
        form.$("button").click();
        $("[data-test-id='order-success']").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));

    }
    @Test
    void ShouldWarnNameIsNull(){
        open("http://localhost:9999/");
        SelenideElement form = $("#root > div > form");
        form.$("button").click();
        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
    @Test
    void ShouldWarnPhoneIsNull(){
        open("http://localhost:9999/");
        SelenideElement form = $("#root > div > form");
        form.$("[data-test-id='name'] input").setValue("Васи-лий");
        form.$("button").click();
        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
    @Test
    void ShouldWarnCheckboxIsOff(){
        open("http://localhost:9999/");
        SelenideElement form = $("#root > div > form");
        form.$("[data-test-id='name'] input").setValue("Василий");
        form.$("[data-test-id='phone'] input").setValue("+71234567890");
        form.$("button").click();
        $(".input_invalid [role='presentation']").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
    @Test
    void ShouldWarnPhoneInvalidLatin(){
        open("http://localhost:9999/");
        SelenideElement form = $("#root > div > form");
        form.$("[data-test-id='name'] input").setValue("Vasilii");//латиница
        form.$("button").click();
        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void ShouldWarnPhoneInvalidSymbols(){
        open("http://localhost:9999/");
        SelenideElement form = $("#root > div > form");
        form.$("[data-test-id='name'] input").setValue("Василий");//восклицательный знак
        form.$("button").click();
        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void ShouldWarnPhoneInvalidNumbers(){
        open("http://localhost:9999/");
        SelenideElement form = $("#root > div > form");
        form.$("[data-test-id='name'] input").setValue("Василий 1!");//цифры
        form.$("button").click();
        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void ShouldWarnPhoneInvalid(){
        open("http://localhost:9999/");
        SelenideElement form = $("#root > div > form");
        form.$("[data-test-id='name'] input").setValue("Васи-лий");
        form.$("[data-test-id='phone'] input").setValue("81234567890");//без +, с 8
        form.$("button").click();
        $(".input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}