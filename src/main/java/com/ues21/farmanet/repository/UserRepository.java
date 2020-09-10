package com.ues21.farmanet.repository;

import com.ues21.farmanet.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.email = :username")
    public UserEntity getUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM UserEntity u where u.roleId = 2")
    public List<UserEntity> getApprovers();
}