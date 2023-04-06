package io.charkchalk.backend.repository.search;

import io.charkchalk.backend.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TagSearchRepository {

    @Autowired
    private EntityManager entityManager;

    public Page<Tag> searchKeyword(String searchName, Pageable pageable) {
        String[] searchNameArray = searchName.split(" ");

        StringBuilder queryString = new StringBuilder("SELECT t FROM Tag t WHERE ");
        for (int i = 0; i < searchNameArray.length; i++) {
            queryString.append("t.name LIKE :name").append(i);
            if (i != searchNameArray.length - 1) {
                queryString.append(" AND ");
            }
        }

        TypedQuery<Tag> query = entityManager.createQuery(queryString.toString(), Tag.class);
        for (int i = 0; i < searchNameArray.length; i++) {
            query.setParameter("name" + i, "%" + searchNameArray[i] + "%");
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Tag> tags = query.getResultList();
        return new PageImpl<>(tags, pageable, tags.size());
    }
}
