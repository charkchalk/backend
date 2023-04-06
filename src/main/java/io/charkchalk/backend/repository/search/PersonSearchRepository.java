package io.charkchalk.backend.repository.search;

import io.charkchalk.backend.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PersonSearchRepository {

    @Autowired
    private EntityManager entityManager;

    public Page<Person> searchKeyword(String searchName, Pageable pageable) {
        String[] searchNameArray = searchName.split(" ");

        StringBuilder queryString = new StringBuilder("SELECT p FROM Person p WHERE ");
        for (int i = 0; i < searchNameArray.length; i++) {
            queryString.append("p.name LIKE :name").append(i);
            if (i != searchNameArray.length - 1) {
                queryString.append(" AND ");
            }
        }

        TypedQuery<Person> query = entityManager.createQuery(queryString.toString(), Person.class);
        for (int i = 0; i < searchNameArray.length; i++) {
            query.setParameter("name" + i, "%" + searchNameArray[i] + "%");
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Person> persons = query.getResultList();
        return new PageImpl<>(persons, pageable, persons.size());
    }
}
