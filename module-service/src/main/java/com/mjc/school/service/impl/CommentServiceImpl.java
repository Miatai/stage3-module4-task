package com.mjc.school.service.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.service.CommentService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper mapper;

    @Autowired
    public CommentServiceImpl(
            final CommentRepository commentRepository,
            final CommentMapper mapper) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<CommentDtoResponse> readAll() {
        return mapper.modelListToDtoList(commentRepository.readAll());
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDtoResponse readById(Long id) {
        return commentRepository
                .readById(id)
                .map(mapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), id)));
    }

    @Override
    @Transactional
    public CommentDtoResponse create(CommentDtoRequest createRequest) {
        Comment model = mapper.dtoToModel(createRequest);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        model.setCreatedDate(date);
        model.setLastUpdatedDate(date);
        model = commentRepository.create(model);
        return mapper.modelToDto(model);
    }

    @Override
    @Transactional
    public CommentDtoResponse update(CommentDtoRequest updateRequest) {
        if (commentRepository.existById(updateRequest.id())) {
            Comment model = mapper.dtoToModel(updateRequest);
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            model.setLastUpdatedDate(date);
            model = commentRepository.update(model);
            return mapper.modelToDto(model);
        } else {
            throw new NotFoundException(String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (commentRepository.existById(id)) {
            return commentRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(COMMENT_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDtoResponse> readByNewsId(Long newsId) {
        return mapper.modelListToDtoList(commentRepository.readByNewsId(newsId));
    }
}
