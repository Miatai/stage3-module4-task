package com.mjc.school.service.impl;

import static com.mjc.school.service.exceptions.ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST;
import static com.mjc.school.service.exceptions.ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mjc.school.repository.AuthorRepository;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.query.NewsSearchQueryParams;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.NewsMapper;
import com.mjc.school.service.query.NewsQueryParams;
import com.mjc.school.service.validator.Valid;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final NewsMapper mapper;

    @Autowired
    public NewsServiceImpl(
            final NewsRepository newsRepository,
            final AuthorRepository authorRepository,
            final NewsMapper mapper) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDtoResponse> readAll() {
        return mapper.modelListToDtoList(newsRepository.readAll());
    }

    @Override
    @Transactional(readOnly = true)
    public NewsDtoResponse readById(final Long id) {
        return newsRepository
                .readById(id)
                .map(mapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), id)));
    }

    @Override
    @Transactional
    public NewsDtoResponse create(@Valid NewsDtoRequest createRequest) {
        if (authorRepository.existById(createRequest.authorId())) {
            News model = mapper.dtoToModel(createRequest);
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            model.setCreatedDate(date);
            model.setLastUpdatedDate(date);
            model = newsRepository.create(model);
            return mapper.modelToDto(model);
        } else {
            throw new NotFoundException(
                    String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), createRequest.authorId()));
        }
    }

    @Override
    @Transactional
    public NewsDtoResponse update(@Valid NewsDtoRequest updateRequest) {
        if (authorRepository.existById(updateRequest.authorId())) {
            if (newsRepository.existById(updateRequest.id())) {
                News model = mapper.dtoToModel(updateRequest);
                LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
                model.setLastUpdatedDate(date);
                model = newsRepository.update(model);
                return mapper.modelToDto(model);
            } else {
                throw new NotFoundException(
                        String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
            }
        } else {
            throw new NotFoundException(
                    String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.authorId()));
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (newsRepository.existById(id)) {
            return newsRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsDtoResponse> readByQueryParams(NewsQueryParams queryParams) {
        NewsSearchQueryParams searchQueryParams = new NewsSearchQueryParams(
                queryParams.tagNames(),
                queryParams.tagIds(),
                queryParams.authorName(),
                queryParams.title(),
                queryParams.content());
        return mapper.modelListToDtoList(newsRepository.readBySearchParams(searchQueryParams));
    }
}
