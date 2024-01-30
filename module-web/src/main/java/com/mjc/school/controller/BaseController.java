package com.mjc.school.controller;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BaseController<T, R, K> {

    List<R> readAll(Integer offset, Integer limit, String sortField, String sortType);

    R readById(K id);

    R create(T createRequest);

    R update(K id, T updateRequest);

    R patch(K id, T updateRequest);

    void deleteById(K id);
}
