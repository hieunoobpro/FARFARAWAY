package com.example.farfaraway.controller;

import com.example.farfaraway.entity.Blog;
import com.example.farfaraway.entity.Comment;
import com.example.farfaraway.entity.User;
import com.example.farfaraway.exception.NotFoundException;
import com.example.farfaraway.repository.BlogRepository;
import com.example.farfaraway.repository.CommentRepository;
import com.example.farfaraway.repository.UserRepository;
import com.example.farfaraway.service.CommentService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private CommentService commentService;

    @PostMapping("/comments")
    public String addComment(@RequestParam("blogId") Integer blogId,
                             @RequestParam("text") String text,
                             Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.findByEmail(username);
            if (user != null) {
                Blog blog = blogRepository.findById(blogId)
                        .orElseThrow(() -> new NotFoundException("Not found blog with id = " + blogId));
                if (blog != null) {
                    Comment comment = new Comment();
                    comment.setUser(user);
                    comment.setBlog(blog);
                    comment.setContent(text);
                    commentRepository.save(comment);
                }
            }
        }
        return "redirect:/blog-detail?id=" + blogId;
    }

    // Controller method để xóa comment
    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId,
                                                Principal principal) {
        System.out.println("abcsdfdsgdgheeheheherheh!");
        User userDetails = userRepository.findByEmail(principal.getName());
        Comment comment = commentService.getCommentById(commentId);
        if (comment.getUser().getName().equals(userDetails.getName())) {
            commentService.deleteComment(commentId);
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized to delete this comment", HttpStatus.UNAUTHORIZED);
        }
    }
    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Integer commentId,
                                                @RequestBody String updatedText) {
        // Tìm comment trong cơ sở dữ liệu bằng commentId
        Comment commentToUpdate = commentService.getCommentById(commentId);

        if (commentToUpdate != null) {
            // Cập nhật nội dung comment
            commentToUpdate.setContent(updatedText);
            // Thực hiện cập nhật ngày sửa (nếu cần)
            commentToUpdate.setUpdatedAt(LocalDateTime.now()); // Giả sử bạn muốn cập nhật thời gian sửa

            // Lưu comment đã được cập nhật vào cơ sở dữ liệu
            commentRepository.save(commentToUpdate);

            return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
        }
    }

}
