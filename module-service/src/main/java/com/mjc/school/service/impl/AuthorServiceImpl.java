package com.mjc.school.service.impl;

import static com.mjc.school.service.exceptions.ServiceErrorCode.AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID;
import static com.mjc.school.service.exceptions.ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.AuthorMapper;
import com.mjc.school.service.validator.Valid;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper mapper;

    @Autowired
    public AuthorServiceImpl(
            final AuthorRepository authorRepository,
            final AuthorMapper mapper) {
        this.authorRepository = authorRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDtoResponse> readAll() {
        return mapper.modelListToDtoList(authorRepository.readAll());
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDtoResponse readById(Long id) {
        return authorRepository
                .readById(id)
                .map(mapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), id)));
    }

    @Override
    @Transactional
    public AuthorDtoResponse create(@Valid AuthorDtoRequest createRequest) {
        Author model = mapper.dtoToModel(createRequest);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        model.setCreatedDate(date);
        model.setLastUpdatedDate(date);
        model = authorRepository.create(model);
        return mapper.modelToDto(model);
    }

    @Override
    @Transactional
    public AuthorDtoResponse update(@Valid AuthorDtoRequest updateRequest) {
        if (authorRepository.existById(updateRequest.id())) {
            Author model = mapper.dtoToModel(updateRequest);
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            model.setLastUpdatedDate(date);
            model = authorRepository.update(model);
            return mapper.modelToDto(model);
        } else {
            throw new NotFoundException(
                    String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long newsId) {
        if (authorRepository.existById(newsId)) {
            return authorRepository.deleteById(newsId);
        } else {
            throw new NotFoundException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), newsId));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDtoResponse readByNewsId(Long newsId) {
        return authorRepository.readByNewsId(newsId)
                .map(mapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(
                                String.format(AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID.getMessage(), newsId)));
    }
}
