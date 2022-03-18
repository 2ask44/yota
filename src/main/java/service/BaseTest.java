package service;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import steps.Steps;

public class BaseTest {

    public Steps steps = new Steps();

    @BeforeClass(description = "Получение Токена")
    public void getToken() {
         steps.login();
    }
}
