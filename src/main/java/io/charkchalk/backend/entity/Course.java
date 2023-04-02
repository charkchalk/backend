package io.charkchalk.backend.entity;

import io.charkchalk.backend.entity.range.DateRange;
import io.charkchalk.backend.entity.range.TimeRange;
import lombok.*;
import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

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

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @Column(name = "code")
    private String code;

    @ManyToOne
    @JoinColumn(name = "on_branch_id", nullable = false)
    private Branch onBranch;

    @ManyToOne
    @JoinColumn(name = "predecessor_id")
    private Course predecessor;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "description")
    private String description;

    @Column(name = "link")
    private URL link;

    @Column(name = "credit")
    private Integer credit;

    @ManyToOne
    @JoinColumn(name = "date_id")
    private DateRange dateRange;

    @ManyToMany
    @JoinTable(name = "courses_tags",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id"))
    @ToString.Exclude
    private Collection<Tag> tags = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "courses_places",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "places_id"))
    @ToString.Exclude
    private Collection<Place> places = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "courses_time_ranges",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "time_ranges_id"))
    @ToString.Exclude
    private Collection<TimeRange> timeRanges = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "courses_persons",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @ToString.Exclude
    private Collection<Person> hosts = new ArrayList<>();

    @OneToMany(mappedBy = "predecessor")
    @ToString.Exclude
    private Collection<Course> child = new ArrayList<>();
}
