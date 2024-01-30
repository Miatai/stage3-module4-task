package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CommentController implements BaseController<CommentDtoRequest, CommentDtoResponse, Long> {
    private final CommentService commentService;

    @Autowired
    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    @CommandHandler(operation = 16)
    public List<CommentDtoResponse> readAll() {
        return commentService.readAll();
    }

    @Override
    @CommandHandler(operation = 17)
    public CommentDtoResponse readById(@CommandParam(name = "id") Long id) {
        return commentService.readById(id);
    }

    @Override
    @CommandHandler(operation = 18)
    public CommentDtoResponse create(@CommandBody CommentDtoRequest createRequest) {
        return commentService.create(createRequest);
    }

    @Override
    @CommandHandler(operation = 19)
    public CommentDtoResponse update(@CommandBody CommentDtoRequest updateRequest) {
        return commentService.update(updateRequest);
    }

    @Override
    @CommandHandler(operation = 20)
    public boolean deleteById(@CommandParam(name = "id") Long id) {
        return commentService.deleteById(id);
    }

    @CommandHandler(operation = 23)
    public List<CommentDtoResponse> readByNewsId(@CommandParam(name = "newsId") Long newsId) {
        return commentService.readByNewsId(newsId);
    }
}
