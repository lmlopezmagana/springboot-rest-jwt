package net.openwebinars.springboot.restjwt.dto;

import lombok.Builder;
import net.openwebinars.springboot.restjwt.note.model.Note;

@Builder
public record GetNoteDto(
        Long id, String title, String content
) {

    public static GetNoteDto of (Note n) {
        return GetNoteDto.builder()
                .id(n.getId())
                .title(n.getTitle())
                .content(n.getContent())
                .build();
    }

}
