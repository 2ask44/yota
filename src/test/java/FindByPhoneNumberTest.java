import org.testng.annotations.Test;
import service.BaseTest;

public class FindByPhoneNumberTest extends BaseTest {
    @Test(description = "Тест №5 - Поиск кастомера по Phone", dataProvider = "api")

    public void findByPhoneNumber(String token) {
        steps.getCustomerByPhone(
                steps.postCustomer(
                        steps.getPhonesList(token),
                        token)
                , token);
    }
}
