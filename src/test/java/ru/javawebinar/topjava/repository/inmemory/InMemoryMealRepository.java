package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.Util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    // Map  userId -> (mealId-> meal)
    //private final Map<Integer, Map<Integer, Meal>> usersMealsMap = new ConcurrentHashMap<>();
    //мультимапа - Guava multimap
    //ключ - userId
    //private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, InMemoryBaseRepository<Meal>> usersMealsMap = new ConcurrentHashMap<>();

    {
        var userMeals = new InMemoryBaseRepository<Meal>();
        MealTestData.meals.forEach(meal -> userMeals.map.put(meal.getId(), meal));
        usersMealsMap.put(UserTestData.USER_ID, userMeals);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        //Map<Integer, Meal> meals = usersMealsMap.computeIfAbsent(userId, ConcurrentHashMap::new);
        //если ключика userId нет, то мы создаем новый ConcurrentHashMap
        /*if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }*/
        Objects.requireNonNull(meal, "meal must not be null");
        var meals = usersMealsMap.computeIfAbsent(userId, uid -> new InMemoryBaseRepository<>());
        return meals.save(meal);
    }


    @PostConstruct
    //аннотация отвечает за то, что происходит после создания бина
    //обычно используется перед методом init()
    public void postConstruct() {
        log.info("+++ PostConstruct");
    }

    @PreDestroy
    //аннотация отвечает за то, что происходит перед удалением бина
    //обычно используется перед методом destroy()
    public void preDestroy() {
        log.info("+++ PreDestroy");
    }

    @Override
    public boolean delete(int id, int userId) {
        var meals = usersMealsMap.get(userId);
        return meals != null && meals.delete(id);
    }

    @Override
    public Meal get(int id, int userId) {
        var meals = usersMealsMap.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return filterByPredicate(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime));
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filterByPredicate(userId, meal -> true);
    }

    private List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        //Map<Integer, Meal> meals = usersMealsMap.get(userId);
        //return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                //необходимо проверять на отсутствие юзера
                //в случае его отсутствия возвращаем пустой список
                //если есть - сортируем по времени и разворачиваем, чтобы последняя еда была на верху списка
                //meals.values().stream()
        var meals = usersMealsMap.get(userId);
        return meals == null ? Collections.emptyList() :
                meals.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}