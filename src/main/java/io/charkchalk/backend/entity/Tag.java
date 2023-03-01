package io.charkchalk.backend.entity;

import io.charkchalk.backend.entity.enums.TagLimit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "tags", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "tag_limit"})
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated
    @Column(name = "tag_limit")
    private TagLimit tagLimit;

    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private Collection<Course> courses = new ArrayList<>();

    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private Collection<Organization> organizations = new ArrayList<>();
}
