package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

public class Util {
    private Util() {
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
        //слева интервал открытый - включаем в значение, слева закрытый - не включаем
    }
}