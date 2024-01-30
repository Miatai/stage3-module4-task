package com.mjc.school.service.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record NewsDtoResponse(
        Long id,
        String title,
        String content,
        LocalDateTime createdDate,
        LocalDateTime lastUpdatedDate,
        AuthorDtoResponse authorDto,
        List<TagDtoResponse> tagDtos,
        List<CommentDtoResponse> commentDtos) {
}
