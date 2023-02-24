package io.charkchalk.backend.json.person;

import io.charkchalk.backend.entity.Person;
import io.charkchalk.backend.exception.FieldNotValidItem;
import io.charkchalk.backend.json.JsonConverter;
import io.charkchalk.backend.json.PageJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PersonConverter {
    public Person convertToEntity(BasePersonJson basePersonJson) {
        Person person = new Person();
        person.setName(basePersonJson.getName());
        person.setDescription(basePersonJson.getDescription());
        person.setLink(basePersonJson.getLink());
        return person;
    }

    public PersonJson convertToJson(Person person) {
        PersonJson personJson = new PersonJson();
        personJson.setId(person.getId());
        personJson.setName(person.getName());
        personJson.setDescription(person.getDescription());
        personJson.setLink(person.getLink());
        return personJson;
    }

    public PageJson<PersonJson> convertToPageJson(Page<Person> persons) {
        PageJson<PersonJson> pageJson = new PageJson<>();
        pageJson.setTotalPages(1);
        pageJson.setCurrentPage(0);

        if (!persons.isEmpty()) {
            for (Person person : persons) {
                pageJson.getContent().add(convertToJson(person));
            }
        }
        return pageJson;
    }

    public void updateEntity(Person person, BasePersonJson basePersonJson) {
        person.setName(basePersonJson.getName());
        person.setDescription(basePersonJson.getDescription());
        person.setLink(basePersonJson.getLink());
    }

    public static void checkPageable(Pageable pageable) {
        Set<String> possibleProperty = new HashSet<>();
        possibleProperty.add("name");

        List<FieldNotValidItem> fieldNotValidItems =
                JsonConverter.checkPageableSortProperty(pageable, possibleProperty, "Person");

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
    }
}
