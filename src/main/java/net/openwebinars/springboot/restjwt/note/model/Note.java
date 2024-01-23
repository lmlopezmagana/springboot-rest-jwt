package net.openwebinars.springboot.restjwt.note.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;

    @CreatedBy
    private String author;

    private boolean important;

    @ElementCollection
    @Builder.Default
    List<String> tags = new ArrayList<>();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

}
