package com.assignment.board.repository;

import com.assignment.board.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    @Query("SELECT a FROM Attachment a where a.article.id = :ArticleId order by a.created_datetime desc")
    List<Attachment> findByArticleId(Long ArticleId);
}
