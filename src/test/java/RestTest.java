import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.Test;
import pojos.Pojo;
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
/*
    @Test(description = "Тест №3 - Получение списка phoneIdsList свободных комеров")
    @Story("ТЕСТЫ API YOTA")
    public void createCustomer() {
        List<String> phonesList = steps.getPhonesList();
        List<String>listId=steps.phoneIdsList(phonesList);
        System.out.println("Полученные ID:" + listId);
    }
*/
    @Test(description = "Тест №3 - Получение списка phoneIdsList свободных комеров")
    @Story("ТЕСТЫ API YOTA")
    public void createCustomer() {
        List<String> phonesList = steps.getPhonesList();
        Pojo phoneAndID = new Pojo();
        phoneAndID=steps.phoneIdsList(phonesList);
        System.out.println("Полученные Phone и ID:" + phoneAndID.phone + " " + " " +phoneAndID.id);
    }

    @Test(description = "Тест №4 - Получить кастомера по ID")
    @Story("ТЕСТЫ API YOTA")
    public void checkCustomerById() {
    List<String> phonesList = steps.getPhonesList();
    Pojo phoneAndID=new Pojo();
    phoneAndID=steps.phoneIdsList(phonesList);
    String listId=phoneAndID.id;
    steps.getCustomerById(listId);}

   //  @Test(description = "Тест №4 - Получить кастомера по ID")
   //  @Story("ТЕСТЫ API YOTA")
   // public void checkCustomerById() {
        // List<String> phonesList = steps.getPhonesList();
        // List<String>listId=steps.phoneIdsList(phonesList);
       //
    // steps.getCustomerById(listId);}

    //@Test(description="Изменеие статуса кастомера ")
    //public void ChangeCustomerStatus() {steps.changeCustomerStatus(token);}


}

