package com.mjc.school.repository.impl;

import com.mjc.school.repository.TagRepository;
import com.mjc.school.repository.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TagDBRepository extends AbstractDBRepository<Tag, Long> implements TagRepository {

    @Override
    void update(Tag prevState, Tag nextState) {
        prevState.setName(nextState.getName());
    }

    @Override
    public List<Tag> readByNewsId(Long newsId) {
        TypedQuery<Tag> typedQuery = entityManager
            .createQuery("SELECT t FROM Tag t INNER JOIN t.news n WHERE n.id=:newsId", Tag.class)
            .setParameter("newsId", newsId);
        return typedQuery.getResultList();
    }
}
