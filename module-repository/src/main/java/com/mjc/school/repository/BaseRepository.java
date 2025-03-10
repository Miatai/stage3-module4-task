package com.mjc.school.repository;

import java.util.List;
import java.util.Optional;

import com.mjc.school.repository.model.BaseEntity;
import org.springframework.data.domain.Pageable;

public interface BaseRepository<T extends BaseEntity<K>, K> {

    List<T> readAll(Pageable pageable);

    Optional<T> readById(K id);

    T create(T entity);

    T update(T entity);

    boolean deleteById(K id);

    boolean existById(K id);

    T getReference(K id);
}
