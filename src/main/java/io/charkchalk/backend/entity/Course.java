package io.charkchalk.backend.entity;

import io.charkchalk.backend.entity.range.DateRange;
import io.charkchalk.backend.entity.range.TimeRange;
import lombok.*;
import javax.persistence.*;
import java.net.URL;
import java.util.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "on_branch_id")
    private Branch onBranch;

    @ManyToOne
    @JoinColumn(name = "predecessor_id")
    private Course predecessor;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "link")
    private URL link;

    @Column(name = "credit")
    private Integer credit;

    @ManyToOne
    @JoinColumn(name = "date_id")
    private DateRange dateRange;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "courses_tags",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id"))
    @ToString.Exclude
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "courses_places",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "places_id"))
    @ToString.Exclude
    private Set<Place> places = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "courses_time_ranges",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "time_ranges_id"))
    @ToString.Exclude
    private Set<TimeRange> timeRanges = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "courses_persons",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @ToString.Exclude
    private Set<Person> hosts = new HashSet<>();

    @OneToMany(mappedBy = "predecessor")
    @ToString.Exclude
    private Set<Course> child = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        setUuid(java.util.UUID.randomUUID());
    }
}
