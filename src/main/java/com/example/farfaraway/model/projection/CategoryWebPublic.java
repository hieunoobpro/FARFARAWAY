package com.example.farfaraway.model.projection;

import com.example.farfaraway.entity.Category;
import lombok.RequiredArgsConstructor;

public interface CategoryWebPublic {
    Integer getId();

    String getName();

    Integer getUsed();

    @RequiredArgsConstructor
    class CategoryWebPublicImpl implements CategoryWebPublic {
        private final Category category;

        @Override
        public Integer getId() {
            return this.category.getId();
        }

        @Override
        public String getName() {
            return this.category.getName();
        }

        @Override
        public Integer getUsed() {
            return null;
        }

    }

    static CategoryWebPublic of(Category category) {
        return new CategoryWebPublicImpl(category);
    }
}
