import org.testng.annotations.Test;
import service.BaseTest;
import io.qameta.allure.Epic;

public class RestTest extends BaseTest {


    @Test(description = "Пользователь получает список свободных номеров ")
    public void checkEmptyPhone() {//steps.getEmptyPhone(token);
        steps.retry();}

    @Test(description = "Пользователь создает нового кастомера")
    public void checkCustomer() {steps.postCustomer(token);}

    @Test(description = "Поиск кастомера по ID")
    public void checkCustomerById() {steps.getCustomerById(token);}

    @Test(description="Изменеие статуса кастомера ")
    public void ChangeCustomerStatus() {steps.changeCustomerStatus(token);}


}

