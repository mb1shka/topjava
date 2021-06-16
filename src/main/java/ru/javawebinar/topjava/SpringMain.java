package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.service.UserService;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
        //можно посмотреть все бины, которые есть в контексте спринга

//        UserRepository userRepository = (UserRepository) appCtx.getBean("inmemoryUserRepository");
        //это - взять из контекста бины по имени

        UserRepository userRepository = appCtx.getBean(UserRepository.class);
        //это - взять бины из контекста по интерфейсу, которые бины реализуют
        userRepository.getAll();

        UserService userService = appCtx.getBean(UserService.class);
        userService.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

        appCtx.close();
        //обязательно нужно закрыть контекст
    }
}
