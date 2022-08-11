package com.assignment.board.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleListSearchCondition {
    private String startDateTime;
    private String emdDateTime;
    private String boardName;
}
