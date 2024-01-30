package com.mjc.school.controller.impl;

import com.mjc.school.controller.CommentController;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1.0/comments")
public class CommentRestController implements CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentRestController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDtoResponse> readAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "sort-field", defaultValue = "id", required = false) String sortField,
            @RequestParam(value = "sortType", defaultValue = "ASC", required = false) String sortType
    ) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.fromString(sortType), sortField));
        return commentService.readAll(pageable);
    }

    @Override
    @GetMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse readById(@PathVariable Long id) {
        return commentService.readById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDtoResponse create(@RequestBody CommentDtoRequest createRequest) {
        return commentService.create(createRequest);
    }

    @Override
    @PutMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDtoResponse update(@PathVariable Long id, @RequestBody CommentDtoRequest updateRequest) {
        return commentService.update(updateRequest);
    }

    @Override
    @PatchMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CommentDtoResponse patch(@PathVariable Long id, @RequestBody CommentDtoRequest updateRequest) {
        return commentService.patch(updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id:\\d+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
    }

    @Override
    @GetMapping(value = "/news/{newsId:\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDtoResponse> readByNewsId(@PathVariable Long newsId) {
        return commentService.readByNewsId(newsId);
    }
}
