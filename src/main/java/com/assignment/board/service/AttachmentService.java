package com.assignment.board.service;

import com.assignment.board.entity.Article;
import com.assignment.board.entity.Attachment;
import com.assignment.board.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    @Transactional
    public ResponseEntity<?> registerFile(Long articleId, MultipartFile file) {
        return new ResponseEntity<>("파일이 업로드 되었습니다.", HttpStatus.CREATED);
    }

    @Transactional
    public void sampleRegisterFile(Article article) {
        for (int i = 0; i < 3; i++) {
            String location = "test url " + i;
            Attachment attachment = new Attachment(article, location);
            attachmentRepository.save(attachment);
        }
    }

    @Transactional
    public void sampleDeleteFile(Long articleId) {
        List<Attachment> attachmentList = attachmentRepository.findByArticleId(articleId);
        attachmentRepository.deleteAll(attachmentList);
    }
}
