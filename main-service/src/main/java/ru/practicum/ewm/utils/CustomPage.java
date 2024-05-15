package ru.practicum.ewm.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CustomPage {
    public static Pageable getPage(int from, int size, Sort sort) {
        return PageRequest.of(from / size, size, sort);
    }

    public static Pageable getPage(int from, int size) {
        return PageRequest.of(from / size, size);
    }
}
