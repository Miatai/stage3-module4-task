package com.mjc.school.controller.impl;

import com.mjc.school.controller.AuthorController;
import com.mjc.school.service.AuthorService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/authors")
public class AuthorRestController implements AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorRestController(final AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDtoResponse> readAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "sort-field", defaultValue = "id", required = false) String sortField,
            @RequestParam(value = "sort-type", defaultValue = "ASC", required = false) String sortType
    ) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.fromString(sortType), sortField));
        return authorService.readAll(pageable);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse readById(@PathVariable Long id) {
        return authorService.readById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDtoResponse create(@RequestBody AuthorDtoRequest dtoRequest) {
        return authorService.create(dtoRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse update(@PathVariable Long id,  @RequestBody AuthorDtoRequest dtoRequest) {
        return authorService.update(dtoRequest);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse patch(@PathVariable Long id,  @RequestBody AuthorDtoRequest dtoRequest) {
        return authorService.patch(dtoRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
    }

    @Override
    @GetMapping(value = "/news/{newsId:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse readByNewsId(@PathVariable Long newsId) {
        return authorService.readByNewsId(newsId);
    }
}
