package net.openwebinars.springboot.restjwt.note.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
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
    private String author;
    private boolean important;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

}
