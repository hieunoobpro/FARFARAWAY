package com.example.farfaraway.service;

import com.example.farfaraway.entity.Blog;
import com.example.farfaraway.entity.Category;
import com.example.farfaraway.entity.User;
import com.example.farfaraway.exception.NotFoundException;
import com.example.farfaraway.repository.BlogRepository;
import com.example.farfaraway.repository.CategoryRepository;
import com.example.farfaraway.repository.UserRepository;
import com.example.farfaraway.security.CustomUserDetails;
import com.github.slugify.Slugify;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public BlogService(BlogRepository blogRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public Page<Blog> findAll(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("publishedAt").descending());
        return blogRepository.findByStatus(true, pageable);
    }

    public List<Blog> searchByTitle(String title) {
        return blogRepository.findByTitleContainingIgnoreCase(title);
    }


    public Blog findByIdAndSlug(Integer id, String slug) {
        return blogRepository.findByIdAndSlugAndStatusTrue(id, slug)
                .orElseThrow(() -> new NotFoundException("Cannot find blog"));
    }

    public Page<Blog> getAllBlogs(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        return blogRepository.findAll(pageable);
    }

    public Page<Blog> getAllBlogsOfUserById(Integer page, Integer limit) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        return blogRepository.findByUser_IdOrderByCreatedAtDesc(
                customUserDetails.getId(),
                pageable
        );
    }

    public Blog createBlog(com.example.farfaraway.model.request.UpsertBlogRequest request) {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Tao blog
        Slugify slugify = Slugify.builder().build();
        Blog blog = Blog.builder()
                .title(request.getTitle())
                .slug(slugify.slugify(request.getTitle()))
                .content(request.getContent())
                .description(request.getDescription())
                .thumbnail(request.getThumbnail())
                .status(request.getStatus())
                .comments(new ArrayList<>())
                .user(customUserDetails.getUser())
                .build();

        return blogRepository.save(blog);
    }

    public Blog getBlogById(Integer id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found blog with id = " + id));
    }

    public Blog updateBlog(Integer id, com.example.farfaraway.model.request.UpsertBlogRequest request) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found blog with id = " + id));

        // TODO: Validate thông tin (nếu cần thiết) - validation

        // Tìm kiếm category
        List<Category> categories = categoryRepository.findByIdIn(request.getCategoryIds());

        Slugify slugify = Slugify.builder().build();
        blog.setTitle(request.getTitle());
        blog.setSlug(slugify.slugify(request.getTitle()));
        blog.setDescription(request.getDescription());
        blog.setContent(request.getContent());
        blog.setStatus(request.getStatus());
        blog.setThumbnail(request.getThumbnail());

        return blogRepository.save(blog);
    }

    public void deleteBlog(Integer id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found blog with id = " + id));
        blogRepository.delete(blog);
    }
    public Page<Blog> searchByTitlePaged(String title, Pageable pageable) {
        return blogRepository.findByTitleContaining(title, pageable);
    }

    public Blog findById(Integer id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found blog with id = " + id));
        return blog;
    }

    public void saveBlog(Blog blog){
        blogRepository.save(blog);
    }
}
