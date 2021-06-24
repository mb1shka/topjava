package ru.javawebinar.topjava.web.user;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;

import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@Ignore
public class InMemoryAdminRestControllerTest {
    private static final Logger log = LoggerFactory.getLogger(InMemoryAdminRestControllerTest.class);

    private static ConfigurableApplicationContext appCtx;
    private static AdminRestController controller;
    private static InMemoryUserRepository repository;

    @BeforeClass
    //выполняется один раз перед всем тестированием
    //выполняется после того как JUnit создал экхемпляр InMemory... класса (класса, в котором пишется метод)
    public static void beforeClass() {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        //поднимаем контекст Спринга
        log.info("\n{}\n", Arrays.toString(appCtx.getBeanDefinitionNames()));
        //С помощью контекста (DI) мы получаем доступ к классам
        controller = appCtx.getBean(AdminRestController.class);
        repository = appCtx.getBean(InMemoryUserRepository.class);
    }

    @AfterClass
    //выполняется один раз после того, как все тесты прошли
    //выполняется перед тем как JUnit удалит экзампляр класса, в котором мы пишем этот метод
    public static void afterClass() {
        appCtx.close();
    }

    @Before
    //код, отмеченный так, выполняется перед каждым тестом
    public void setup() {
        // re-initialize
        repository.init();
    }

    public void delete() {
        controller.delete(USER_ID);
        Assert.assertNull(repository.get(USER_ID));
    }

    @Test
    public void deleteNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }
}