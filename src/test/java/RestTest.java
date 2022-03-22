import org.testng.annotations.Test;
import service.BaseTest;

import java.util.List;

public class RestTest extends BaseTest {

    @Test(description = "Пользователь получает список свободных номеров ")
    public void checkEmptyPhone() {
        steps.getPhonesList();
    }

    @Test(description = "Проверка списка номеров на наличие не зарегистрированных номеров ")
    public void checkCustomer() {
        List<String> phonesList = steps.getPhonesList();
        steps.phonesListValidation(phonesList);
    }

    @Test(description = "Получение списка phoneIdsList")
    public void createCustomer() {
        List<String> phonesList = steps.getPhonesList();
        List<String>listId=steps.phoneIdsList(phonesList);
        System.out.println("Полученные ID:" + listId);
    }

     //@Test(description = "Поиск кастомера по ID")
    //public void checkCustomerById() {steps.getCustomerById();}

    //@Test(description="Изменеие статуса кастомера ")
    //public void ChangeCustomerStatus() {steps.changeCustomerStatus(token);}


}

