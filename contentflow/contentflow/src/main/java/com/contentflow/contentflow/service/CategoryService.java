package com.contentflow.contentflow.service;

import com.contentflow.contentflow.dto.request.CategoryRequest;
import com.contentflow.contentflow.dto.response.CategoryResponse;
import com.contentflow.contentflow.exception.ResourceNotFoundException;
import com.contentflow.contentflow.model.Category;
import com.contentflow.contentflow.model.User;
import com.contentflow.contentflow.repository.CategoryRepository;
import com.contentflow.contentflow.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public CategoryResponse createCategory(CategoryRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Category category = new Category();
        category.setName(request.getName());
        category.setUser(user);

        Category saved = categoryRepository.save(category);
        return mapToResponse(saved);
    }

    public List<CategoryResponse> getAllCategories(Long userId) {
        List<Category> categories = categoryRepository.findByUserId(userId);
        return categories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id, Long userId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (!category.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }

        return mapToResponse(category);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest request, Long userId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (!category.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }

        category.setName(request.getName());
        Category updated = categoryRepository.save(category);
        return mapToResponse(updated);
    }

    public void deleteCategory(Long id, Long userId) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (!category.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }

        categoryRepository.delete(category);
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}