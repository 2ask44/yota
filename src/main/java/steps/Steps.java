package steps;

import api.Api;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;
import pojos.PhoneAndIdPojo;
import pojos.Pojo;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class Steps {

    Api api = new Api();

    @Step("Получение токена по логину и паролю")
    public String login(String login, String ps) {
        return api.login(login, ps);
    }

    @Step("Получение списка свободных номеров")
    public List<String> getPhonesList(String token) {
        AtomicReference<List<String>> phonesList = new AtomicReference<>();
        await("Подождите пока массив будет не пустой").atMost(5000, TimeUnit.MILLISECONDS)
                .until(() -> {
                    Response response = api.getEmptyPhone(token);
                    boolean success = response.statusCode() == 200 && response.jsonPath().getList("phones").size() > 0;
                    if (success) {
                        phonesList.set(response.body().jsonPath().getList("phones", Pojo.class)
                                .stream().map(p -> Long.toString(p.getPhone())).collect(Collectors.toList()));
                    }
                    return success;
                });
        return phonesList.get();
    }

    @Step("Проверка списка номеров на свободность ")
    public void phonesListValidation(List<String> phonesList, String token) {
        for (String phone : phonesList) {
            Response response = api.postCustomer(phone, token);
            if (response.statusCode() != 200) {
                Assert.fail("В списке номеров(" + phonesList + ")имеется номер:" + phone + ", который точно занят");
            }
        }
    }

    @Step("Получение кастомера (с сохраннием номера и Id в объект)")
    public PhoneAndIdPojo postCustomer(List<String> phonesList, String token) {
        for (int i = 0; i <= phonesList.size() - 1; i++) {
            Response response = api.postCustomer(phonesList.get(i), token);
            if (response.statusCode() == 200) {
                PhoneAndIdPojo phoneAndIdPojo = new PhoneAndIdPojo();
                phoneAndIdPojo.setPhone(Long.parseLong(phonesList.get(i)));
                phoneAndIdPojo.setId(response.path("id"));
                return phoneAndIdPojo;
            }
        }
        Assert.fail("Свободных номеров не имеется");
        return null;
    }

    @Step("Получение кастомера по ID, с ожиданием status=ACTIVE")
    public void getCustomerById(String Id, String token) {
        with().pollInterval(5000, TimeUnit.MILLISECONDS)
                .await("Подождите пока status не измениться на ACTIVE, " +
                        "в течение 2 минут, с частотой запроса 5000 мил.сек.")
                .atMost(2, TimeUnit.MINUTES)
                .until(() -> {
                    Response response = api.getCustomerById(Id, token);
                    return response.body().path("return.status").equals("ACTIVE") &&
                            "string".equals(response.body().path("return.additionalParameters.string"));
                });
    }

    @Step("Поиск Кастомера по номеру телефона")
    public void getCustomerByPhone(PhoneAndIdPojo phoneAndIdPojo, String token) {
        Response response = api.findByPhoneNumber(phoneAndIdPojo.getPhone(), token);
        assertThat("Проверка совпадения ID", response.body().xmlPath()
                .get("Envelope.Body.customerId"), equalTo(phoneAndIdPojo.getId()));
    }

    @Step("Получение")
    public void checkCustomerStatus(String id, String token) {
        api.changeCustomerStatus(id, token);
        assertThat("Проверка статуса", api.getCustomerById(id, token).body()
                .path("return.status"), equalTo("Yes"));
    }

}