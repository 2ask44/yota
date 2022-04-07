import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pojos.PhoneAndIdPojo;
import service.BaseTest;

import java.util.List;

//@DisplayName("ТЕСТЫ API")
public class RestTest extends BaseTest {

    @Test(description = "Тест №1 - Пользователь получает список свободных номеров ")
    @Owner("Андрей Кирник")
    @Story("ТЕСТЫ API YOTA")
    public void checkEmptyPhone() {
        steps.getPhonesList();
    }

    @Test(description = "Тест №2 - Проверка списка номеров на наличие не зарегистрированных номеров ")
    @Owner("Андрей Кирник")
    @Story("ТЕСТЫ API YOTA")
    public void checkCustomer() {
        List<String> phonesList = steps.getPhonesList();
        steps.phonesListValidation(phonesList);
    }

    @Test(description = "Тест №3 - Создание нового кастомера через")
    @Owner("Андрей Кирник")
    @Story("ТЕСТЫ API YOTA")
    public void createCustomer() {
        List<String> phonesList = steps.getPhonesList();
        steps.postCustomer(phonesList);
    }

    @Test(description = "Тест №4 - Получить кастомера по ID")
    @Owner("Андрей Кирник")
    @Story("ТЕСТЫ API YOTA")
    public void checkCustomerById() {
        List<String> phonesList = steps.getPhonesList();
        String id = steps.postCustomer(phonesList).getId();
        steps.getCustomerById(id);
    }

    @Test(description = "Тест №5 - Поиск кастомера по Phone")
    @Owner("Андрей Кирник")
    @Story("ТЕСТЫ API YOTA")
    public void findByPhoneNumber() {
        steps.getCustomerByPhone(steps.postCustomer(steps.getPhonesList()));
    }

    @Test(description = "Тест №6 -  Измениние статуса кастомера по ID")
    @Owner("Андрей Кирник")
    @Story("ТЕСТЫ API YOTA")
    public void checkCustomerStatus() {
        List<String> phonesList = steps.getPhonesList();
        String id = steps.postCustomer(phonesList).getId();
        steps.checkCustomerStatus(id);
    }

}

