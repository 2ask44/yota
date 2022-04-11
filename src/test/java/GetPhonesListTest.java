import org.testng.annotations.Test;
import service.BaseTest;

public class GetPhonesListTest extends BaseTest {

    @Test(description = "Тест №1 - Пользователь получает список свободных номеров", dataProvider = "api")
    public void checkEmptyPhone(String token) {
        steps.getPhonesList(token);
    }
}
