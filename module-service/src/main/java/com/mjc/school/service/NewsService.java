package com.mjc.school.service;

import java.util.List;

import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.query.NewsQueryParams;

public interface NewsService extends BaseService<NewsDtoRequest, NewsDtoResponse, Long> {
    List<NewsDtoResponse> readByQueryParams(NewsQueryParams queryParams);
}
