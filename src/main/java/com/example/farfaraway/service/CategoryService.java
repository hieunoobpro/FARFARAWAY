package com.example.farfaraway.service;

import com.example.farfaraway.entity.Blog;
import com.example.farfaraway.entity.Category;
import com.example.farfaraway.entity.Category;
import com.example.farfaraway.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    public Page<Category> getAllCatagories(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
        return categoryRepository.findAll(pageable);
    }
}
