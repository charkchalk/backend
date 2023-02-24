package io.charkchalk.backend.service.person;

import io.charkchalk.backend.entity.Person;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.json.person.BasePersonJson;
import io.charkchalk.backend.json.person.PersonConverter;
import io.charkchalk.backend.json.person.PersonJson;
import io.charkchalk.backend.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
public class PersonController {

    @Autowired
    private PersonConverter personConverter;

    @Autowired
    private PersonRepository personRepository;

    @PostMapping("/api/person")
    public ResponseEntity<PersonJson> createPlace(@Valid @RequestBody BasePersonJson basePersonJson) {
        Person person = personConverter.convertToEntity(basePersonJson);
        person = personRepository.save(person);
        return ResponseEntity.ok(personConverter.convertToJson(person));
    }

    @GetMapping("/api/person")
    public ResponseEntity<PageJson<PersonJson>> getPerson(Pageable pageable) {
        PersonConverter.checkPageable(pageable);
        return ResponseEntity.ok(personConverter.convertToPageJson(personRepository.findAll(pageable)));
    }

    @GetMapping("/api/person/{id}")
    public ResponseEntity<PersonJson> getPerson(@PathVariable UUID id) {
        Optional<Person> personOptional = personRepository.findById(id);
        return personOptional.map(value -> ResponseEntity.ok(personConverter.convertToJson(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/api/person/{id}")
    public ResponseEntity<PersonJson> putPerson(@PathVariable UUID id,
                                                @Valid @RequestBody BasePersonJson basePersonJson) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Person person = personOptional.get();
        personConverter.updateEntity(person, basePersonJson);
        personRepository.save(person);
        return ResponseEntity.ok(personConverter.convertToJson(person));
    }

    @DeleteMapping("/api/person/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable UUID id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        personRepository.delete(personOptional.get());
        return ResponseEntity.ok().build();
    }
}
