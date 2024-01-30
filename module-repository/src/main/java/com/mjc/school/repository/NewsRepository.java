package com.mjc.school.repository;

import java.util.List;

import com.mjc.school.repository.model.News;
import com.mjc.school.repository.query.NewsSearchQueryParams;

public interface NewsRepository extends BaseRepository<News, Long> {
    List<News> readBySearchParams(NewsSearchQueryParams searchQueryParams);
}
