package io.charkchalk.backend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "persons")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "link", nullable = false)
    private URL link;

    @ManyToMany(mappedBy = "hosts")
    @ToString.Exclude
    private Collection<Course> courses = new ArrayList<>();
}