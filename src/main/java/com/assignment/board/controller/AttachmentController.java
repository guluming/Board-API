package com.assignment.board.controller;

import com.assignment.board.service.AttachmentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/{post_id}/files")
    public ResponseEntity<?> registerFile(
            @PathVariable("post_id") Long articleId,
            final MultipartFile file) {
        return attachmentService.registerFile(articleId, file);
    }
}
