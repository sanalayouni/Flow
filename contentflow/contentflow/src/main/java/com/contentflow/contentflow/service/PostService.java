package com.contentflow.contentflow.service;

import com.contentflow.contentflow.dto.request.PostRequest;
import com.contentflow.contentflow.dto.response.PostResponse;
import com.contentflow.contentflow.exception.ResourceNotFoundException;
import com.contentflow.contentflow.model.Category;
import com.contentflow.contentflow.model.Post;
import com.contentflow.contentflow.model.PostStatus;
import com.contentflow.contentflow.model.User;
import com.contentflow.contentflow.repository.CategoryRepository;
import com.contentflow.contentflow.repository.PostRepository;
import com.contentflow.contentflow.repository.UserRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public PostResponse createPost(PostRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setStatus(request.getStatus());
        post.setScheduledDate(request.getScheduledDate());
        post.setDeleted(false);
        post.setUser(user);
        post.setCategory(resolveCategory(request.getCategoryId(), userId));

        Post savedPost = postRepository.save(post);
        return mapToResponse(savedPost);
    }

    public Page<PostResponse> getAllPosts(Long userId, PostStatus status, Long categoryId, Pageable pageable) {
        Specification<Post> specification = buildPostSpecification(userId, status, categoryId);
        return postRepository.findAll(specification, pageable)
                .map(this::mapToResponse);
    }

    public PostResponse getPostById(Long id, Long userId) {
        Post post = findUserPost(id, userId);
        return mapToResponse(post);
    }

    public PostResponse updatePost(Long id, PostRequest request, Long userId) {
        Post post = findUserPost(id, userId);

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setStatus(request.getStatus());
        post.setScheduledDate(request.getScheduledDate());
        post.setCategory(resolveCategory(request.getCategoryId(), userId));

        Post updatedPost = postRepository.save(post);
        return mapToResponse(updatedPost);
    }

    public void deletePost(Long id, Long userId) {
        Post post = findUserPost(id, userId);
        post.setDeleted(true);
        postRepository.save(post);
    }

    private Post findUserPost(Long postId, Long userId) {
        Specification<Post> specification = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("id"), postId),
                criteriaBuilder.equal(root.get("user").get("id"), userId),
                criteriaBuilder.isFalse(root.get("deleted"))
        );

        return postRepository.findOne(specification)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
    }

    private Category resolveCategory(Long categoryId, Long userId) {
        if (categoryId == null) {
            return null;
        }

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        if (!category.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }

        return category;
    }

    private Specification<Post> buildPostSpecification(Long userId, PostStatus status, Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            predicates.add(criteriaBuilder.isFalse(root.get("deleted")));

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private PostResponse mapToResponse(Post post) {
        Category category = post.getCategory();

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getStatus(),
                post.getScheduledDate(),
                category != null ? category.getId() : null,
                category != null ? category.getName() : null,
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}