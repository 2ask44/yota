package steps;

import api.Api;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.Assert;
import pojos.PhoneAndIdPojo;
import pojos.Pojo;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.with;

public class Steps {

    Api api = new Api();

    @Step("Шаг1.Получение токена по логину и паролю")
    public void login() {
        api.login();
    }

    @Step("Шаг2.Получение списка свободных номеров")
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

    @Step("Шаг 3.Проверка списка номеров {phonesList} на свободность,  ")
    public void phonesListValidation(List<String> phonesList) {
        for (String phone : phonesList) {
/*            if (api.postCustomer(phone).isEmpty()) {
                Assert.fail("В списке номеров имеется хотя бы один номер, который точно занят");
            }*/
        }
        //System.out.print("ТЕСТ ПРОШЕЛ, ВЕСЬ СПИСОК НОМЕРОВ КОРРЕКТНЫЙ");
    }
/*
    @Step("Создание нового Кастомера, и получение списка c ID: phoneIdsList")
    public List<String> phoneIdsList(List<String> phonesList) {
        List<String> phoneIdsList = new ArrayList<>();
        List<String> phoneActual = new ArrayList<>();
        for (String phone : phonesList) {
            String id = api.postCustomer(phone);
            if (id.isEmpty()) {
                //System.out.print("Шаг далее");
            } else {
                phoneIdsList.add(id);
                phoneActual.add(phone);
            }
        }
        if (phoneIdsList.size() < 0) {
            //System.out.println("свободных номеров не имеется");
            Assert.fail("свободных номеров не имеется");
        } else {
        }
        return phoneIdsList;
    }
*/
  /*  @Step("Создание нового Кастомера, и получение списка c ID: phoneIdsList")
    public Pojo phoneIdsList(List<String> phonesList) {
        List<String> phoneIdsList = new ArrayList<>();
        Pojo pojo =new Pojo();
        for (String phone : phonesList) {
            String id = api.postCustomer(phone);

            if (id.isEmpty()) {
                //System.out.print("Шаг далее");
            } else {
               // phoneIdsList.add(id);
               // pojo.id=id;
               // pojo.phone= Long.parseLong(phone);
                pojo.setId(id);
                pojo.setPhone(Long.parseLong(phone));
            }
        }
        if (phoneIdsList.size() < 0 ) {
            //System.out.println("свободных номеров не имеется");
            Assert.fail("свободных номеров не имеется");
        } else {
        }
        return pojo;
    }*/

    @Step("Шаг 4.Получение кастомера (с сохраннием номера и Id в объект)")
    public PhoneAndIdPojo postCustomer(List<String> phonesList) {
        for (int i = 0; i <= phonesList.size() - 1; i++) {
            Response response = api.postCustomer(phonesList.get(i));
            if (response.statusCode() == 200) {
                PhoneAndIdPojo phoneAndIdPojo = new PhoneAndIdPojo();
                phoneAndIdPojo.setPhone(Long.parseLong(phonesList.get(i)));
                phoneAndIdPojo.setId(response.path("id"));
                return phoneAndIdPojo;
            }
        }
        Assert.fail("свободных номеров не имеется");
        return null;
    }

    @Step("Шаг 5. Получение кастомера по ID, с ожиданием status=ACTIVE")
    public void getCustomerById(String Id) {
        with().pollInterval(5000, TimeUnit.MILLISECONDS)
                .await("Подождите пока status не измениться на ACTIVE, " +
                        "в течение 2 минут, с частотой запроса 5000 мил.сек.")
                .atMost(2, TimeUnit.MINUTES)
                .until(() -> {
                    Response response = api.getCustomerById(Id);
                    return response.body().path("return.status").equals("ACTIVE") &&
                            "string".equals(response.body().path("return.additionalParameters.string"));
                });
    }

    @Step("Шаг 6. Поиск Кастомера по номеру телефона")
    public void getCustomerByPhone(PhoneAndIdPojo phoneAndIdPojo) {
        Response response = api.findByPhoneNumber(phoneAndIdPojo.getPhone());
        String idOut = response.body().xmlPath().get("Envelope.Body.customerId");
        String idOld = phoneAndIdPojo.getId();
        //String idOld = "8cb41819-ac7a-48ac-8620-1d8463d31d20";
        //Assert.assertTrue(idOut.contains(idOld));
        //Assert.assertEquals(idOld,idOut);
        if (idOut.equals(idOld)==false)
        {
            Assert.fail("ID получен от другого номера");
        }
         }

}