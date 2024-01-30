package com.mjc.school.service;

import java.util.List;

import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;

public interface TagService extends BaseService<TagDtoRequest, TagDtoResponse, Long> {
    List<TagDtoResponse> readByNewsId(Long newsId);
}
