package com.example.farfaraway.repository;


import com.example.farfaraway.entity.Blog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    // 1. Tìm tất cả các blog có status = true và sắp xếp theo publishedAt giảm dần (mới nhất trước) và phân trang
    Page<Blog> findByStatusTrueOrderByPublishedAtDesc(Pageable pageable);

    Page<Blog> findByStatus(Boolean status, Pageable pageable);

    // 2. Tìm kiếm blog theo từ khóa chứa trong title
    List<Blog> findByTitleContainingIgnoreCase(String title);


    // 6. Lấy chi tiết bài viết theo id và slug và status = true
    Optional<Blog> findByIdAndSlugAndStatusTrue(Integer id, String slug);

    Optional<Blog> findByIdAndSlugAndStatus(Integer id, String slug, Boolean status);

    Page<Blog> findByUser_IdOrderByCreatedAtDesc(Integer userId, Pageable pageable);

    Page<Blog> findByTitleContaining(String title, Pageable pageable);
}