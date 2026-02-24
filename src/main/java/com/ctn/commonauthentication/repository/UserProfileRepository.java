package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.GlobalUser;
import com.ctn.commonauthentication.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByGlobalUser(GlobalUser globalUser);

    Optional<UserProfile> findByGlobalUserEmail(String email);
}
