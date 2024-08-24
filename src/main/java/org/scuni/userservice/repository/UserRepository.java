package org.scuni.userservice.repository;

import org.scuni.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query("update User u set u.imageFilename = :imageFilename where u.id = :id")
    void updateImageFilenameById(@Param("imageFilename") String imageFilename, @Param("id")Integer id);
}
