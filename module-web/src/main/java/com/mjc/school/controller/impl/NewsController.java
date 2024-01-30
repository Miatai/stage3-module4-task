package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.controller.annotation.CommandQueryParams;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.query.NewsQueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class NewsController implements BaseController<NewsDtoRequest, NewsDtoResponse, Long> {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Override
    @CommandHandler(operation = 1)
    public List<NewsDtoResponse> readAll() {
        return newsService.readAll();
    }

    @Override
    @CommandHandler(operation = 2)
    public NewsDtoResponse readById(@CommandParam(name = "id") Long id) {
        return newsService.readById(id);
    }

    @Override
    @CommandHandler(operation = 3)
    public NewsDtoResponse create(@CommandBody NewsDtoRequest dtoRequest) {
        return newsService.create(dtoRequest);
    }

    @Override
    @CommandHandler(operation = 4)
    public NewsDtoResponse update(@CommandBody NewsDtoRequest dtoRequest) {
        return newsService.update(dtoRequest);
    }

    @Override
    @CommandHandler(operation = 5)
    public boolean deleteById(@CommandParam(name = "id") Long id) {
        return newsService.deleteById(id);
    }

    @CommandHandler(operation = 24)
    public List<NewsDtoResponse> readByQueryParams(@CommandQueryParams NewsQueryParams queryParams) {
        return newsService.readByQueryParams(queryParams);
    }
}
