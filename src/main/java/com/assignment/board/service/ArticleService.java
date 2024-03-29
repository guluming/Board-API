package com.assignment.board.service;

import com.assignment.board.dto.request.ArticleListSearchCondition;
import com.assignment.board.dto.request.ArticleRequest;
import com.assignment.board.dto.response.ArticleResponse;
import com.assignment.board.entity.Article;
import com.assignment.board.entity.Attachment;
import com.assignment.board.entity.Board;
import com.assignment.board.repository.ArticleQueryRepository;
import com.assignment.board.repository.ArticleRepository;
import com.assignment.board.repository.AttachmentRepository;
import com.assignment.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;
    private final ArticleQueryRepository articleQueryRepository;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentService attachmentService;

    @Transactional
    public ResponseEntity<?> registerPost(Long boardId, ArticleRequest registerPost) {
        final Optional<Board> board = boardRepository.findById(boardId);
        if (!board.isPresent()) {
            return new ResponseEntity<>("해당 게시판이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        } else {
            Article article = new Article(board.get(), registerPost);
            Article saveArticle = articleRepository.save(article);
            attachmentService.sampleRegisterFile(saveArticle);
            return new ResponseEntity<>("게시글이 등록 되었습니다.", HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> getPostList(ArticleListSearchCondition articleListSearchCondition) {
            List<Article> startDateTimeArticles = articleQueryRepository.findAllArticle(articleListSearchCondition);
            return new ResponseEntity<>(this.articleResponseList(startDateTimeArticles), HttpStatus.OK);
    }

    public List<ArticleResponse> articleResponseList (List<Article> articles) {
        List<ArticleResponse> articleResponseList = new ArrayList<>();
        for (Article article : articles) {
            ArticleResponse articleResponse = new ArticleResponse();
            articleResponse.setName(article.getBoard().getName());
            articleResponse.setTitle(article.getTitle());
            articleResponse.setCreated_datetime(article.getCreated_datetime());

            List<Attachment> locations = attachmentRepository.findByArticleId(article.getId());
            List<ArticleResponse.location> locationList = new ArrayList<>();
            ArticleResponse.location articleLocation = new ArticleResponse.location();
            articleLocation.setLocation(locations.get(0).getLocation());
            locationList.add(articleLocation);
            articleResponse.setLocations(locationList);
            articleResponseList.add(articleResponse);
        }
        return articleResponseList;
    }

    public ResponseEntity<?> getPost(Long ArticleId) {
        Optional<Article> article = articleRepository.findById(ArticleId);
        if (!article.isPresent()) {
            return new ResponseEntity<>("해당 게시글은 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        } else {
            ArticleResponse articleResponse = new ArticleResponse();
            articleResponse.setName(article.get().getBoard().getName());
            articleResponse.setTitle(article.get().getTitle());
            articleResponse.setCreated_datetime(article.get().getCreated_datetime());

            List<Attachment> locations = attachmentRepository.findByArticleId(ArticleId);
            List<ArticleResponse.location> locationList = new ArrayList<>();
            for (Attachment location : locations) {
                ArticleResponse.location articleLocation = new ArticleResponse.location();
                articleLocation.setLocation(location.getLocation());
                locationList.add(articleLocation);
            }
            articleResponse.setLocations(locationList);
            article.get().view_count();
            return new ResponseEntity<>(articleResponse, HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<?> deletePost(Long ArticleId) {
        Optional<Article> article = articleRepository.findById(ArticleId);
        if (!article.isPresent()) {
            return new ResponseEntity<>("해당 게시글은 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        } else {
            attachmentService.sampleDeleteFile(ArticleId);
            articleRepository.deleteById(ArticleId);
            return new ResponseEntity<>("게시글이 삭제 되었습니다.", HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<?> updatePost(Long ArticleId, ArticleRequest updatePost) {
        Optional<Article> article = articleRepository.findById(ArticleId);
        if (!article.isPresent()) {
            return new ResponseEntity<>("해당 게시글은 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        } else if (!updatePost.getTitle().equals(article.get().getTitle()) && !updatePost.getContent().equals(article.get().getContent())) {
            article.get().articleTitleUpdate(updatePost.getTitle());
            article.get().articleContentUpdate(updatePost.getContent());
            return new ResponseEntity<>("게시글 제목과 내용이 수정 되었습니다.", HttpStatus.OK);
        } else if (!updatePost.getTitle().equals(article.get().getTitle()) && updatePost.getContent().equals(article.get().getContent())) {
            article.get().articleTitleUpdate(updatePost.getTitle());
            return new ResponseEntity<>("게시글 제목이 수정 되었습니다.", HttpStatus.OK);
        } else if (updatePost.getTitle().equals(article.get().getTitle()) && !updatePost.getContent().equals(article.get().getContent())) {
            article.get().articleContentUpdate(updatePost.getContent());
            return new ResponseEntity<>("게시글 내용이 수정 되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("입력하신 내용이 모두 똑같습니다.", HttpStatus.OK);
        }
    }
}
