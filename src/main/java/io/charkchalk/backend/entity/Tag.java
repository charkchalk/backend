package io.charkchalk.backend.entity;

import io.charkchalk.backend.entity.enums.TagLimit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "tags")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

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
