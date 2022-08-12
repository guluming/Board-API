package com.assignment.board.repository;

import com.assignment.board.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("select a from Article a where a.created_datetime > :startDateTime order by a.created_datetime desc")
    List<Article> findAllByStartDateTime(LocalDate startDateTime);
    @Query("select a from Article a where a.created_datetime < :endDateTime order by a.created_datetime desc")
    List<Article> findAllByEndDateTime(LocalDate endDateTime);
    @Query("select a from Article a where a.board.name like %:boardName% order by a.created_datetime desc")
    List<Article> findAllByBoardName(String boardName);
    @Query("select a from Article a where a.created_datetime > :startDateTime and" +
            " a.created_datetime < :endDateTime and" +
            " a.board.name like %:boardName%" +
            " order by a.created_datetime desc")
    List<Article> findAllArticle(LocalDate startDateTime, LocalDate endDateTime, String boardName);
}
