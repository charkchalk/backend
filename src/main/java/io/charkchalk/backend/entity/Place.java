package io.charkchalk.backend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "places", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "parent_id"})
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Place parent;

    @OneToMany(mappedBy = "parent")
    @ToString.Exclude
    private Collection<Place> children;

    @ManyToMany(mappedBy = "places")
    @ToString.Exclude
    private Collection<Course> courses = new ArrayList<>();
}
