package net.openwebinars.springboot.restjwt.note.service;

import lombok.RequiredArgsConstructor;
import net.openwebinars.springboot.restjwt.dto.GetNoteDto;
import net.openwebinars.springboot.restjwt.dto.NotesGroupedByTagsDto;
import net.openwebinars.springboot.restjwt.note.model.Note;
import net.openwebinars.springboot.restjwt.note.repo.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository repository;

    public List<NotesGroupedByTagsDto> notesGroupedByTagsDtoList(String author) {
        List<Note> notes = repository.findByAuthor(author);

        List<NotesGroupedByTagsDto> result = null;


        if (notes.size() > 0) {
            List<String> differentsTags =
                    notes.stream()
                            .map(Note::getTags)
                            .flatMap(List::stream)
                            .distinct()
                            .collect(Collectors.toList());


            result = new ArrayList<>();

            for (String tag : differentsTags) {
                List<GetNoteDto> notesByTag =
                        notes.stream()
                                .filter(n -> n.getTags().contains(tag))
                                .map(GetNoteDto::of)
                                .collect(Collectors.toList());

                result.add(
                        NotesGroupedByTagsDto.builder()
                                .tag(tag)
                                .notes(notesByTag)
                                .build()
                );

            }


            return result;
        }

        return result;










    }



}
