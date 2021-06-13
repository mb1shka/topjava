package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*

        int caloriesPerDay = 2000;

        List<Meal> meals = MealsUtil.getMeals();

        Map<LocalDate, Integer> caloriesSumByDate = meals.stream().
                collect(Collectors.groupingBy(meal -> meal.getDateTime().
                        toLocalDate(), Collectors.summingInt(Meal::getCalories)));

        MealsUtil.getMeals().stream()
                .map(meal -> new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        caloriesSumByDate.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));

*/


      /*  req.setAttribute("meals", MealsUtil.getMeals());*/
      /*  req.getRequestDispatcher("//meals.jsp").forward(req, resp);*/
    }
}
