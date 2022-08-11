package com.assignment.board.entity;

import com.assignment.board.dto.request.BoardRequest;
import com.assignment.board.utill.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Board extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @OneToMany(mappedBy = "board")
    private List<Article> articles = new ArrayList<>();

    public void Board(BoardRequest param) {
        this.name = param.getName();
    }
}
