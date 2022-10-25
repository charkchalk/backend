package io.charkchalk.backend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "places")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Place parent;

    @OneToMany(mappedBy = "parent")
    @ToString.Exclude
    private Collection<Place> children;

    @OneToMany(mappedBy = "place")
    @ToString.Exclude
    private Collection<Course> courses = new ArrayList<>();
}
