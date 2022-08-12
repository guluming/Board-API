package com.assignment.board.repository;

import com.assignment.board.dto.request.ArticleListSearchCondition;
import com.assignment.board.entity.Article;
import com.assignment.board.entity.QArticle;
import com.assignment.board.entity.QBoard;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.assignment.board.entity.QArticle.article;
import static com.assignment.board.entity.QBoard.board;

@RequiredArgsConstructor
@Repository
public class ArticleQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Article> findAllArticle(ArticleListSearchCondition articleListSearchCondition) {
        return queryFactory
                .selectFrom(article)
                .innerJoin(article.board, board)
                .where(betweenDateTime(articleListSearchCondition.getStartDateTime(), articleListSearchCondition.getEndDateTime()),
                        containsBoardName(articleListSearchCondition.getBoardName()))
                .orderBy(article.created_datetime.desc())
                .fetch();
    }

    private BooleanExpression containsBoardName(String boardName) {
        if (boardName != null && !boardName.trim().isEmpty()) {
           return board.name.contains(boardName);
        }
        return null;
    }

    private BooleanExpression betweenDateTime(String startDateTime, String endDateTime) {
        if (startDateTime != null &&
                endDateTime != null &&
                !startDateTime.trim().isEmpty() &&
                !endDateTime.trim().isEmpty()) {
            return article.created_datetime
                    .between(LocalDate.parse(startDateTime, DateTimeFormatter.ISO_DATE),
                            LocalDate.parse(endDateTime, DateTimeFormatter.ISO_DATE));
        } else if (startDateTime != null && !startDateTime.trim().isEmpty()) {
            return article.created_datetime.after(LocalDate.parse(startDateTime, DateTimeFormatter.ISO_DATE));
        } else if (endDateTime != null && !endDateTime.trim().isEmpty()) {
            return article.created_datetime.before(LocalDate.parse(endDateTime, DateTimeFormatter.ISO_DATE));
        } else {
            return null;
        }
    }
}
