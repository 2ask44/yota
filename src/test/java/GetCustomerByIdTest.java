import org.testng.annotations.Test;
import service.BaseTest;

import java.util.List;

public class GetCustomerByIdTest extends BaseTest {

    @Test(description = "Тест №4 - Получить кастомера по ID", dataProvider = "api")
    public void checkCustomerById(String token) {
        List<String> phonesList = steps.getPhonesList(token);
        String id = steps.postCustomer(phonesList, token).getId();
        steps.getCustomerById(id, token);
    }
}
