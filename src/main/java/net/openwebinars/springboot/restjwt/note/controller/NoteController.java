package net.openwebinars.springboot.restjwt.note.controller;

import lombok.RequiredArgsConstructor;
import net.openwebinars.springboot.restjwt.note.model.Note;
import net.openwebinars.springboot.restjwt.note.repo.NoteRepository;
import net.openwebinars.springboot.restjwt.user.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Note>> getAll(@AuthenticationPrincipal User user) {
        // Utilizamos un método comun para devolver la respuesta de todos los List<Note>
        //return buildResponseOfAList(repository.findAll());
        return buildResponseOfAList(repository.findByAuthor(user.getId().toString()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Note> getById(@PathVariable Long id) {
        /*
            El método ResponseEntity.of recibe como argumento un Optional<?> y devuelve
                - 200 Ok si Optional.isPresent() == true
                - 404 Not Found si Optional.isEmpty() == true
         */
        return ResponseEntity.of(repository.findById(id));
    }


    @GetMapping("/author/{author}")
    public ResponseEntity<List<Note>> getByAuthor(@PathVariable String author) {
        // Utilizamos un método comun para devolver la respuesta de todos los List<Note>
        return buildResponseOfAList(repository.findByAuthor(author));
    }

    /**
     * Este método sirve para devolver la respuesta de un List<Note>
     * @param list Lista que vendrá de una consulta en el repositorio
     * @return 404 si la lista está vacía, 200 OK si la lista tiene elementos
     */
    private ResponseEntity<List<Note>> buildResponseOfAList(List<Note> list) {

        if (list.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(list);


    }

    @PostMapping("/")
    public ResponseEntity<Note> createNewNote(@RequestBody Note note) {

        Note created = repository.save(note);

        URI createdURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId()).toUri();

        /*
            Habitualmente, la respuesta correcta de una petición POST es 201 Created.
            Adicionalmente, se puede devolver un encabezado Location con la URI que
            nos permite realizar la petición GET al recurso recién creado.
         */
        return ResponseEntity
                .created(createdURI)
                .body(created);

    }

    @PreAuthorize("""
            @noteRepository.findById(#id)
                .orElse(new net.openwebinars.springboot.restjwt.note.model.Note()).author == 
                                                        authentication.principal.getId().toString()
            """)
    @PutMapping("/{id}")
    public ResponseEntity<Note> edit(@PathVariable Long id, @RequestBody Note edited) {

        return ResponseEntity.of(
                repository.findById(id)
                        .map(note -> {
                            note.setTitle(edited.getTitle());
                            note.setContent(edited.getContent());
                            //note.setAuthor(edited.getAuthor());
                            note.setImportant(edited.isImportant());
                            return repository.save(note);
                        }));



    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        // Dejamos esta línea comentada para provocar un error 500 si eliminamos dos veces un mismo recurso
        //if (repository.existsById(id))
        repository.deleteById(id);

        return ResponseEntity.noContent().build();

    }




}

