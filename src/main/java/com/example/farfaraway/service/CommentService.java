package com.example.farfaraway.service;

import com.example.farfaraway.entity.Blog;
import com.example.farfaraway.entity.Comment;
import com.example.farfaraway.entity.User;
import com.example.farfaraway.exception.NotFoundException;
import com.example.farfaraway.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment getCommentById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found comment with id = " + id));
    }

    public void deleteComment(Integer id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found blog with id = " + id));
        commentRepository.delete(comment);
    }
    public Page<Comment> getAllCommentsByPage(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        return commentRepository.findAll(pageable);
    }
}
