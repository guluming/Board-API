package com.assignment.board.entity;

import com.assignment.board.dto.request.AttachmentRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String location;
    @CreatedDate
    private LocalDate created_datetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public Attachment(Article article, AttachmentRequest param) {
        this.article = article;
        this.location = param.getLocation();
    }

    public Attachment(Article article, String location) {
        this.article = article;
        this.location = location;
    }
}
