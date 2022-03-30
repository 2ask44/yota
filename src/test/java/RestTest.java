import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pojos.PhoneAndIdPojo;
import service.BaseTest;

import java.util.List;

//@DisplayName("ТЕСТЫ API")
public class RestTest extends BaseTest {

    @Test(description = "Тест №1 - Пользователь получает список свободных номеров ")
    @Story("ТЕСТЫ API YOTA")
    public void checkEmptyPhone() {
        steps.getPhonesList();
    }

    @Test(description = "Тест №2 - Проверка списка номеров на наличие не зарегистрированных номеров ")
    @Story("ТЕСТЫ API YOTA")
    public void checkCustomer() {
        List<String> phonesList = steps.getPhonesList();
        steps.phonesListValidation(phonesList);
    }

    @Test(description = "Тест №3 - Создание нового кастомера через")
    @Story("ТЕСТЫ API YOTA")
    public void createCustomer() {
        List<String> phonesList = steps.getPhonesList();
        steps.postCustomer(phonesList);
    }

    @Test(description = "Тест №4 - Получить кастомера по ID")
    @Story("ТЕСТЫ API YOTA")
    public void checkCustomerById() {
        List<String> phonesList = steps.getPhonesList();
        String id = steps.postCustomer(phonesList).getId();
        steps.getCustomerById(id);

    }

    @Test(description = "Тест №5 - Поиск кастомера по Phone")
    @Story("ТЕСТЫ API YOTA")
    public void findByPhoneNumber() {
        steps.getCustomerByPhone(steps.postCustomer(steps.getPhonesList()));
    }


}

