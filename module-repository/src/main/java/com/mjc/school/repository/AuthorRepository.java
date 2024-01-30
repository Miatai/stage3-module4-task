package com.mjc.school.repository;

import java.util.Optional;

import com.mjc.school.repository.model.Author;

public interface AuthorRepository extends BaseRepository<Author, Long> {
    Optional<Author> readByNewsId(Long newsId);
}
