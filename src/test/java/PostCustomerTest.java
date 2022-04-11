import org.testng.annotations.Test;
import service.BaseTest;

import java.util.List;

public class PostCustomerTest extends BaseTest {
    @Test(description = "Тест №3 - Создание нового кастомера через", dataProvider = "api")
    public void createCustomer(String token) {
        List<String> phonesList = steps.getPhonesList(token);
        steps.postCustomer(phonesList, token);
    }
}
