package com.assignment.board.entity;

import com.assignment.board.dto.request.ArticleRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private int view_count = 0;
    @CreatedDate
    private LocalDateTime created_datetime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "article")
    private List<Attachment> Attachment = new ArrayList<>();

    public void Article(ArticleRequest param) {
        this.title = param.gerTitle();
        this.content = param.getContent();
    }

    public void ArticleUpdate(ArticleRequest param) {
        this.title = param.gerTitle();
        this.content = param.getContent();
    }

    public void view_count() {
        view_count++;
    }
}
