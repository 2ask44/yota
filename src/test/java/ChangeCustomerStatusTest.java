import org.testng.annotations.Test;
import service.BaseTest;

import java.util.List;

public class ChangeCustomerStatusTest extends BaseTest {
    @Test(description = "Тест №6 -  Измениние статуса кастомера по ID", dataProvider = "api")
    public void checkCustomerStatus(String token) {
        List<String> phonesList = steps.getPhonesList(token);
        String id = steps.postCustomer(phonesList, token).getId();
        steps.checkCustomerStatus(id, token);
    }
}
