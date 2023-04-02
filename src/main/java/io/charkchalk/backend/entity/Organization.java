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
@Table(name = "organizations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "parent_id"})
})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Organization {
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
    private Organization parent;

    @ManyToMany
    @JoinTable(name = "organizations_tags",
            joinColumns = @JoinColumn(name = "organization_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id"))
    @ToString.Exclude
    private Collection<Tag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "parent")
    @ToString.Exclude
    private Collection<Organization> children;

    @OneToMany(mappedBy = "organization", orphanRemoval = true)
    @ToString.Exclude
    private Collection<Course> courses = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        setUuid(java.util.UUID.randomUUID());
    }
}
