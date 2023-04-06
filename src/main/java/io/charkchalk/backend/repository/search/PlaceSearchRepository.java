package io.charkchalk.backend.repository.search;

import io.charkchalk.backend.entity.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PlaceSearchRepository {

    @Autowired
    private EntityManager entityManager;

    public Page<Place> searchKeyword(String searchName, Pageable pageable) {
        String[] searchNameArray = searchName.split(" ");

        StringBuilder queryString = new StringBuilder("SELECT p FROM Place p WHERE ");
        for (int i = 0; i < searchNameArray.length; i++) {
            queryString.append("p.name LIKE :name").append(i);
            if (i != searchNameArray.length - 1) {
                queryString.append(" AND ");
            }
        }

        TypedQuery<Place> query = entityManager.createQuery(queryString.toString(), Place.class);
        for (int i = 0; i < searchNameArray.length; i++) {
            query.setParameter("name" + i, "%" + searchNameArray[i] + "%");
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Place> places = query.getResultList();
        return new PageImpl<>(places, pageable, places.size());
    }
}
