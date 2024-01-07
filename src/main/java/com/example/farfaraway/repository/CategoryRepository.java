package com.example.farfaraway.repository;

import com.example.farfaraway.entity.Category;
import com.example.farfaraway.model.dto.CategoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByTour_Status(Boolean status);


    // Lấy danh sách category và số lượng bài viết áp dụng sử dụng native query
    @Query(nativeQuery = true, name = "getAllCategoryDtoNQ")
    List<CategoryDto> getAllCategoryDtoNQ();

    List<Category> findByIdIn(List<Integer> categoryIds);
}