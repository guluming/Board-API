package com.assignment.board.service;

import com.assignment.board.dto.request.BoardRequest;
import com.assignment.board.entity.Board;
import com.assignment.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public ResponseEntity<?> registerBoard(BoardRequest registerRequest) {
        Board board = new Board(registerRequest);
        boardRepository.save(board);
        return new ResponseEntity<>("게시판 생성이 완료 되었습니다.", HttpStatus.CREATED);
    }
}
