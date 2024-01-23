package net.openwebinars.springboot.restjwt.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record NotesGroupedByTagsDto(
        String tag,
        List<GetNoteDto> notes
) {
}
