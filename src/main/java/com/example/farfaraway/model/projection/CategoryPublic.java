package com.example.farfaraway.model.projection;

import com.example.farfaraway.entity.Category;
import lombok.RequiredArgsConstructor;

public interface CategoryPublic {
    Integer getId();
    String getName();

    @RequiredArgsConstructor
    class CategoryPublicImpl implements CategoryPublic {
        private final Category category;

        @Override
        public Integer getId() {
            return this.category.getId();
        }

        @Override
        public String getName() {
            return this.category.getName();
        }
    }

    static CategoryPublic of(Category category) {
        return new CategoryPublicImpl(category);
    }
}
