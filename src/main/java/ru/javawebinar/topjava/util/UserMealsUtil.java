package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        System.out.println(" ");
        mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> newMeals = new ArrayList<>();
        boolean moreCaloriesThenNeeded = false;

        for (UserMeal meal : meals) {
            LocalDateTime localDateTime = meal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(localDateTime.toLocalTime(), startTime, endTime)) {
                if (meal.getCalories() > caloriesPerDay) {
                    moreCaloriesThenNeeded = true;
                }
                newMeals.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), moreCaloriesThenNeeded));
            }
        }
        return newMeals;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        List<UserMeal> sortedMeal = meals.stream().filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)).collect(Collectors.toList());

        boolean moreCaloriesThenNeeded = false;
        List<UserMealWithExcess> userMealWithExcessList = new ArrayList<>();
        for (UserMeal meal : sortedMeal) {
            if (meal.getCalories() > caloriesPerDay) {
                moreCaloriesThenNeeded = true;
            }
            userMealWithExcessList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), moreCaloriesThenNeeded));
        }
        return userMealWithExcessList;

        //TODO добавить поиск, превышает ли количество калорий
    }
}
