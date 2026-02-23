package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.GlobalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GlobalUserRepository extends JpaRepository<GlobalUser, Long> {

    Optional<GlobalUser> findByEmail(String email);

    Optional<GlobalUser> findByToken(String token);

    boolean existsByEmail(String email);
}
