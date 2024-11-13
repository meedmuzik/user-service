package org.scuni.userservice.repository;

import org.scuni.userservice.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {
    @Query("MATCH (a:User) WHERE id(a) = $id SET a.imageFilename = $filename")
    void updateImageFilenameById(@Param("filename") String filename, @Param("id") Long id);
}
