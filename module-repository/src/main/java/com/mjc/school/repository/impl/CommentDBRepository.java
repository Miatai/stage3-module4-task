package com.mjc.school.repository.impl;

import com.mjc.school.repository.CommentRepository;
import com.mjc.school.repository.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentDBRepository extends AbstractDBRepository<Comment, Long> implements CommentRepository {

    @Override
    public List<Comment> readByNewsId(Long newsId) {
        TypedQuery<Comment> typedQuery = entityManager
                .createQuery("SELECT c FROM Comment c INNER JOIN c.news n WHERE n.id=:newsId", Comment.class)
                .setParameter("newsId", newsId);
        List<Comment> list = typedQuery.getResultList();
        System.out.println("comments:");
        for (Comment comment: list) {
            System.out.println(comment);
        }
        return list;
    }

    @Override
    void update(Comment prevState, Comment nextState) {
        prevState.setContent(nextState.getContent());
        prevState.setNews(nextState.getNews());
    }
}
