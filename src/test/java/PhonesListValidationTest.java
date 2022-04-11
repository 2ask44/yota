import org.testng.annotations.Test;
import service.BaseTest;

import java.util.List;

public class PhonesListValidationTest extends BaseTest {
    @Test(description = "Тест №2 - Проверка списка номеров на наличие не зарегистрированных номеров", dataProvider = "api")

    public void checkCustomer(String token) {
        List<String> phonesList = steps.getPhonesList(token);
        steps.phonesListValidation(phonesList, token);
    }
}
