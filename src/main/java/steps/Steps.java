package steps;

import api.Api;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojos.Pojo;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import static org.awaitility.Awaitility.await;


public class Steps {

    private Api api = new Api();

    @Step("Получение токена по логину и паролю")
    public void login() {
        api.login();
    }

    @Step("Получение списка свободных номеров  ")
    public List<String> getPhonesList() {
        AtomicReference<List<String>> phonesList = new AtomicReference<>();
        await("Подождите пока массив будет не пустой").atMost(5000, TimeUnit.MILLISECONDS)
                .until(() -> {
                    Response response = api.getEmptyPhone();
                    boolean success = response.statusCode() == 200 && response.jsonPath().getList("phones").size() > 0;
                    if (success) {
                        phonesList.set(response.body().jsonPath().getList("phones", Pojo.class)
                                .stream().map(p -> Long.toString(p.getPhone())).collect(Collectors.toList()));
                    }
                    return success;
                });
        System.out.println(phonesList);
        return phonesList.get();
    }

    @Step("Создание нового Кастомера  по номеру и проверку на свободность")
    public void phonesListValidation(List<String> phonesList) {
        for (String phone : phonesList) {
            if (api.postCustomer(phone).isEmpty()) {
                System.out.print(" ТЕСТ УПАЛ");
                return;
            }
        }

        System.out.print("Заебись");
    }
}