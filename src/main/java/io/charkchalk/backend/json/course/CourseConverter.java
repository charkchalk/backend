package io.charkchalk.backend.json.course;

import io.charkchalk.backend.entity.*;
import io.charkchalk.backend.entity.range.DateRange;
import io.charkchalk.backend.entity.range.TimeRange;
import io.charkchalk.backend.exception.FieldNotValidItem;
import io.charkchalk.backend.json.JsonConverter;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.json.organization.OrganizationConverter;
import io.charkchalk.backend.json.person.PersonConverter;
import io.charkchalk.backend.json.place.PlaceConverter;
import io.charkchalk.backend.json.range.date.DateRangeConverter;
import io.charkchalk.backend.json.range.time.BaseTimeRangeJson;
import io.charkchalk.backend.json.range.time.TimeRangeConverter;
import io.charkchalk.backend.json.tag.TagConverter;
import io.charkchalk.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class CourseConverter {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private DateRangeRepository dateRangeRepository;

    @Autowired
    private OrganizationConverter organizationConverter;

    @Autowired
    private DateRangeConverter dateRangeConverter;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private TimeRangeRepository timeRangeRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private TagConverter tagConverter;

    @Autowired
    private PlaceConverter placeConverter;

    @Autowired
    private TimeRangeConverter timeRangeConverter;

    @Autowired
    private PersonConverter personConverter;

    public Course convertToEntity(RequestCourseJson requestCourseJson) {
        return updateEntity(new Course(), requestCourseJson);
    }

    public CourseJson convertToJson(Course course) {
        CourseJson courseJson = new CourseJson();
        courseJson.setUuid(course.getUuid());
        courseJson.setCode(course.getCode());
        courseJson.setName(course.getName());
        courseJson.setDescription(course.getDescription());

        if (course.getOrganization() != null) {
            courseJson.setOrganization(organizationConverter.convertToJson(course.getOrganization()));
        }

        courseJson.setLink(course.getLink());
        courseJson.setCredit(course.getCredit());

        if (course.getDateRange() != null) {
            courseJson.setDateRange(dateRangeConverter.convertToJson(course.getDateRange()));
        }

        if (!course.getTags().isEmpty()) {
            for (Tag tag : course.getTags()) {
                courseJson.getTags().add(tagConverter.convertToJson(tag));
            }
        }

        if (!course.getPlaces().isEmpty()) {
            for (Place place : course.getPlaces()) {
                courseJson.getPlaces().add(placeConverter.convertToJson(place));
            }
        }

        if (!course.getTimeRanges().isEmpty()) {
            for (TimeRange timeRange : course.getTimeRanges()) {
                courseJson.getTimeRanges().add(timeRangeConverter.convertToJson(timeRange));
            }
        }

        if (!course.getHosts().isEmpty()) {
            for (Person person : course.getHosts()) {
                courseJson.getHosts().add(personConverter.convertToJson(person));
            }
        }

        return courseJson;
    }

    public PageJson<CourseJson> convertToPageJson(Page<Course> courses) {
        PageJson<CourseJson> pageJson = new PageJson<>();
        pageJson.setTotalPages(courses.getTotalPages());
        pageJson.setCurrentPage(courses.getNumber());

        if (!courses.isEmpty()) {
            for (Course course : courses.getContent()) {
                pageJson.getContent().add(convertToJson(course));
            }
        }

        return pageJson;
    }

    public Course updateEntity(Course course, RequestCourseJson requestCourseJson) {
        List<FieldNotValidItem> fieldNotValidItems = new ArrayList<>();

        course.setCode(requestCourseJson.getCode());

        if (requestCourseJson.getPredecessorUuid() != null) {
            Optional<Course> predecessor = courseRepository.findByUuid(requestCourseJson.getPredecessorUuid());
            if (predecessor.isPresent()) {
                course.setPredecessor(predecessor.get());
            } else {
                fieldNotValidItems.add(FieldNotValidItem.entityNotFound("predecessor", "Course",
                        requestCourseJson.getPredecessorUuid().toString()));
            }
        }

        course.setName(requestCourseJson.getName());
        course.setDescription(requestCourseJson.getDescription());

        if (requestCourseJson.getOrganizationUuid() != null) {
            Optional<Organization> organization = organizationRepository
                    .findByUuid(requestCourseJson.getOrganizationUuid());
            if (organization.isPresent()) {
                course.setOrganization(organization.get());
            } else {
                fieldNotValidItems.add(FieldNotValidItem.entityNotFound("organization", "Organization",
                        requestCourseJson.getOrganizationUuid().toString()));
            }
        }

        course.setLink(requestCourseJson.getLink());
        course.setCredit(requestCourseJson.getCredit());

        if (requestCourseJson.getDateRangeUuid() != null) {
            Optional<DateRange> dateRange = dateRangeRepository.findByUuid(requestCourseJson.getDateRangeUuid());
            if (dateRange.isPresent()) {
                course.setDateRange(dateRange.get());
            } else {
                fieldNotValidItems.add(FieldNotValidItem.entityNotFound("dateRange", "DateRange",
                        requestCourseJson.getDateRangeUuid().toString()));
            }
        }

        for (UUID tagUuid : requestCourseJson.getTagsUuid()) {
            Optional<Tag> tag = tagRepository.findByUuid(tagUuid);
            if (tag.isPresent()) {
                course.getTags().add(tag.get());
            } else {
                fieldNotValidItems.add(FieldNotValidItem.entityNotFound("tag", "Tag",
                        tagUuid.toString()));
            }
        }

        for (UUID placeUuid : requestCourseJson.getPlacesUuid()) {
            Optional<Place> place = placeRepository.findByUuid(placeUuid);
            if (place.isPresent()) {
                course.getPlaces().add(place.get());
            } else {
                fieldNotValidItems.add(FieldNotValidItem.entityNotFound("place", "Place",
                        placeUuid.toString()));
            }
        }

        for (BaseTimeRangeJson timeRangeJson : requestCourseJson.getTimeRanges()) {
            Optional<TimeRange> timeRange = timeRangeRepository
                    .findByStartTimeAndEndTimeAndWeekday(timeRangeJson.getStartTime(), timeRangeJson.getEndTime(),
                            timeRangeJson.getWeekday());
            if (timeRange.isPresent()) {
                course.getTimeRanges().add(timeRange.get());
            } else {
                timeRangeRepository.save(timeRangeConverter.convertToEntity(timeRangeJson));
            }
        }

        for (UUID hostUuid : requestCourseJson.getHostsUuid()) {
            Optional<Person> host = personRepository.findByUuid(hostUuid);
            if (host.isPresent()) {
                course.getHosts().add(host.get());
            } else {
                fieldNotValidItems.add(FieldNotValidItem.entityNotFound("host", "Person",
                        hostUuid.toString()));
            }
        }

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
        return course;
    }

    public static void checkPageable(Pageable pageable) {
        Set<String> validSorts = new HashSet<>();
        validSorts.add("name");

        List<FieldNotValidItem> fieldNotValidItems =
            JsonConverter.checkPageableSortProperty(pageable, validSorts, "TimeRange");

        JsonConverter.checkFieldNotValidException(fieldNotValidItems);
    }
}
