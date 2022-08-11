package com.assignment.board.controller;

import com.assignment.board.dto.request.ArticleRequest;
import com.assignment.board.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/{board_id}/post")
    public ResponseEntity<?> registerPost(
            @PathVariable("board_id") Long boardId,
            final @RequestBody ArticleRequest registerPost) {
        return articleService.registerPost(boardId, registerPost);
    }

    @GetMapping("/post")
    public ResponseEntity<?> getPostList(@RequestParam(required = false) String startDateTime,
                                         @RequestParam(required = false) String endDateTime,
                                         @RequestParam(required = false) String boardName) {
        return articleService.getPostList(startDateTime, endDateTime, boardName);
    }

    @GetMapping("/post/{post_id}")
    public ResponseEntity<?> getPost(@PathVariable("post_id") Long ArticleId) {
        return articleService.getPost(ArticleId);
    }

    @DeleteMapping("/post/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable("post_id") Long ArticleId) {
        return articleService.deletePost(ArticleId);
    }

    @PutMapping("/post/{post_id}")
    public ResponseEntity<?> updatePost(@PathVariable("post_id") Long ArticleId,
                                        final @RequestBody ArticleRequest updatePost) {
        return articleService.updatePost(ArticleId, updatePost);
    }
}
