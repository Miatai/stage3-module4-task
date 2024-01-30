package com.mjc.school.controller.impl;

import com.mjc.school.controller.NewsController;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.query.NewsQueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/news")
public class NewsControllerImpl implements NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsControllerImpl(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDtoResponse> readAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "sort-field", defaultValue = "id", required = false) String sortField,
            @RequestParam(value = "sortType", defaultValue = "ASC", required = false) String sortType
    ) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.fromString(sortType), sortField));
        return newsService.readAll(pageable);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDtoResponse readById(@PathVariable Long id) {
        return newsService.readById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDtoResponse create(@RequestBody NewsDtoRequest dtoRequest) {
        return newsService.create(dtoRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDtoResponse update(@PathVariable Long id,  @RequestBody NewsDtoRequest dtoRequest) {
        return newsService.update(dtoRequest);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDtoResponse patch(@PathVariable Long id,  @RequestBody NewsDtoRequest dtoRequest) {
        return newsService.patch(dtoRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
    }

    @Override
    @GetMapping(value = "/params")
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDtoResponse> readByQueryParams(NewsQueryParams queryParams) {
        return newsService.readByQueryParams(queryParams);
    }
}
