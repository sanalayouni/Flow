package com.contentflow.contentflow.repository;

import com.contentflow.contentflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
//this interface extends JpaRepository, which provides CRUD operations for the User entity. The Long type parameter indicates that the primary key of the User entity is of type Long.
public interface UserRepository extends JpaRepository<User, Long> {
}