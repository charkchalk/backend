package io.charkchalk.backend.entity;

import io.charkchalk.backend.entity.range.DateRange;
import io.charkchalk.backend.entity.range.TimeRange;
import lombok.*;
import javax.persistence.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

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

    @Column(name = "credits")
    private Integer credits;

    @ManyToMany
    @JoinTable(name = "courses_tags",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tags_id"))
    @ToString.Exclude
    private Collection<Tag> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "date_id")
    private DateRange dateRange;

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
