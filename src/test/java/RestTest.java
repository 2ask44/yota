import org.testng.annotations.Test;
import service.BaseTest;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class RestTest extends BaseTest {

    @Test(description = "Пользователь получает список свободных номеров ")
    public void checkEmptyPhone() {
        steps.getPhonesList();
    }

    @Test(description = "Пользователь создает нового кастомера")
    public void checkCustomer() {
        List<String> phonesList = steps.getPhonesList();
        steps.phonesListValidation(phonesList);
    }

    //  @Test(description = "Поиск кастомера по ID")
    //public void checkCustomerById() {steps.getCustomerById(token);}

    //@Test(description="Изменеие статуса кастомера ")
    //public void ChangeCustomerStatus() {steps.changeCustomerStatus(token);}


}

