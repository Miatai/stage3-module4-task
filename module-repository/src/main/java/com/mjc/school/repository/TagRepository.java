package com.mjc.school.repository;

import java.util.List;

import com.mjc.school.repository.model.Tag;

public interface TagRepository extends BaseRepository<Tag, Long> {
    List<Tag> readByNewsId(Long newsId);
}
