package org.scuni.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.ArrayList;
import java.util.List;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String keycloakId;

    private String imageFilename;

    @Builder.Default
    private List<Long> commentsIds = new ArrayList<>();

    public boolean isCommentAlreadyExist(Long id) {
        return commentsIds.contains(id);
    }

    public void addComment(Long id) {
        this.commentsIds.add(id);
    }
}
