package service;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import steps.Steps;

public class BaseTest {


    public Steps steps = new Steps();
    public static String tokenAdmin;
    public static String tokenUser;

    @Owner("Андрей Кирник")
    @Story("ТЕСТЫ API YOTA")
    @BeforeSuite(description = "Получение Токена")
    public void getToken() {
        tokenAdmin = steps.login("admin", "password");
        tokenUser = steps.login("user", "password");
    }
    @DataProvider(name = "api", parallel = true)
    public Object[][] getData() {
        return new Object[][]{{tokenUser}, {tokenAdmin}};
    }
}
