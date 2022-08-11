package com.assignment.board.controller;

import com.assignment.board.dto.request.BoardRequest;
import com.assignment.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards")
    public ResponseEntity<?> registerBoard(final @RequestBody BoardRequest registerRequest) {
        return boardService.registerBoard(registerRequest);
    }
}
