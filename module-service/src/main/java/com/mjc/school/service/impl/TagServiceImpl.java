package com.mjc.school.service.impl;

import static com.mjc.school.service.exceptions.ServiceErrorCode.TAG_ID_DOES_NOT_EXIST;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.TagService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.TagMapper;
import com.mjc.school.service.validator.Valid;

@Service
public class TagServiceImpl implements TagService {
    private final TagMapper mapper;
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(
            final TagRepository tagRepository,
            final TagMapper mapper) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDtoResponse> readAll(Pageable pageable) {
        return mapper.modelListToDtoList(tagRepository.readAll(pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public TagDtoResponse readById(final Long id) {
        return tagRepository
                .readById(id)
                .map(mapper::modelToDto)
                .orElseThrow(
                        () -> new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), id)));
    }

    @Override
    @Transactional
    public TagDtoResponse create(@Valid TagDtoRequest createRequest) {
        Tag model = mapper.dtoToModel(createRequest);
        model = tagRepository.create(model);
        return mapper.modelToDto(model);
    }

    @Override
    @Transactional
    public TagDtoResponse update(@Valid TagDtoRequest updateRequest) {
        if (tagRepository.existById(updateRequest.id())) {
            Tag model = mapper.dtoToModel(updateRequest);
            model = tagRepository.update(model);
            return mapper.modelToDto(model);
        } else {
            throw new NotFoundException(
                    String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }
    }

    @Override
    @Transactional
    public TagDtoResponse patch(TagDtoRequest patchRequest) {
        Tag model = tagRepository.readById(patchRequest.id()).orElseThrow(
                () -> new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), patchRequest.id()))
        );
        if(patchRequest.name() != null){
            model.setName(patchRequest.name());
        }
        return mapper.modelToDto(model);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (tagRepository.existById(id)) {
            return tagRepository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), id));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDtoResponse> readByNewsId(Long newsId) {
        return mapper.modelListToDtoList(tagRepository.readByNewsId(newsId));
    }
}
