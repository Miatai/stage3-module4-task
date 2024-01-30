package com.mjc.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T, R, K> {
    List<R> readAll(Pageable pageable);

    R readById(K id);

    R create(T createRequest);

    R update(T updateRequest);

    R patch(T patchRequest);

    boolean deleteById(K id);
}
