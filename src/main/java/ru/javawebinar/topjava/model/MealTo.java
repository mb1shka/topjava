package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealTo {

    private final Integer id;

    //DTO
    //в дз - перенести в отдельный пакет - TO (transfer object)

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;
    //показывает, превышает ли сумма колорий заданного значения, или нет

    public MealTo(Integer id, LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
}
