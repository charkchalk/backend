package io.charkchalk.backend.repository.search;

import io.charkchalk.backend.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class OrganizationSearchRepository {

    @Autowired
    private EntityManager entityManager;

    public Page<Organization> searchKeyword(String searchName, Pageable pageable) {
        String[] searchNameArray = searchName.split(" ");

        StringBuilder queryString = new StringBuilder("SELECT o FROM Organization o WHERE ");
        for (int i = 0; i < searchNameArray.length; i++) {
            queryString.append("o.name LIKE :name").append(i);
            if (i != searchNameArray.length - 1) {
                queryString.append(" AND ");
            }
        }

        TypedQuery<Organization> query = entityManager.createQuery(queryString.toString(), Organization.class);
        for (int i = 0; i < searchNameArray.length; i++) {
            query.setParameter("name" + i, "%" + searchNameArray[i] + "%");
        }

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Organization> organizations = query.getResultList();
        return new PageImpl<>(organizations, pageable, organizations.size());
    }
}
