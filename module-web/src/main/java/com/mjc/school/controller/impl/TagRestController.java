package com.mjc.school.controller.impl;

import com.mjc.school.controller.TagController;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/tags")
public class TagRestController implements TagController {
    private final TagService tagService;

    @Autowired
    public TagRestController(final TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDtoResponse> readAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "sort-field", defaultValue = "id", required = false) String sortField,
            @RequestParam(value = "sort-type", defaultValue = "ASC", required = false) String sortType
    ) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.fromString(sortType), sortField));
        return tagService.readAll(pageable);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse readById(@PathVariable Long id) {
        return tagService.readById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDtoResponse create(@RequestBody TagDtoRequest dtoRequest) {
        return tagService.create(dtoRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse update(@PathVariable Long id, @RequestBody TagDtoRequest dtoRequest) {
        return tagService.update(dtoRequest);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public TagDtoResponse patch(@PathVariable Long id, @RequestBody TagDtoRequest dtoRequest) {
        return tagService.patch(dtoRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
    }

    @Override
    @GetMapping(value = "/news/{newsId:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public List<TagDtoResponse> readByNewsId(@PathVariable Long newsId) {
        return tagService.readByNewsId(newsId);
    }
}
