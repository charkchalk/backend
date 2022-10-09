package io.charkchalk.backend.entity;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "branches")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    // OneToMany
    @OneToMany(mappedBy = "on_branch", orphanRemoval = true)
    @ToString.Exclude
    private Collection<Course> courses = new ArrayList<>();
}