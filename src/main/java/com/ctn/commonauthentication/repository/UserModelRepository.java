package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserModelRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByUserId(String userId);
}
