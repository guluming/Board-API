package com.assignment.board.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ArticleListSearchCondition {
    private String startDateTime;
    private String endDateTime;
    private String boardName;
}
