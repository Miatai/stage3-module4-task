package com.mjc.school.service.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.TagRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;

@Mapper(componentModel = "spring", uses = { AuthorMapper.class, TagMapper.class})
public abstract class NewsMapper {
    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected TagRepository tagRepository;
    @Autowired
    protected CommentRepository commentRepository;

    public abstract List<NewsDtoResponse> modelListToDtoList(List<News> modelList);

    @Mapping(source = "author", target = "authorDto")
    @Mapping(source = "tags", target = "tagDtos")
    @Mapping(source = "comments", target = "commentDtos")
    public abstract NewsDtoResponse modelToDto(News model);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "author", expression =
            "java(authorRepository.getReference(dto.authorId()))")
    @Mapping(target = "tags", expression =
            "java(dto.tagIds().stream().map(tagId -> tagRepository.getReference(tagId)).toList())")
    @Mapping(target = "comments", expression =
            "java(dto.commentIds().stream().map(commentId -> commentRepository.getReference(commentId)).toList())")
    public abstract News dtoToModel(NewsDtoRequest dto);

}

