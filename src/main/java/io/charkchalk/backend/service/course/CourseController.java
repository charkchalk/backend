package io.charkchalk.backend.service.course;

import io.charkchalk.backend.entity.Course;
import io.charkchalk.backend.json.PageJson;
import io.charkchalk.backend.json.course.CourseConverter;
import io.charkchalk.backend.json.course.CourseJson;
import io.charkchalk.backend.json.course.RequestCourseJson;
import io.charkchalk.backend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CourseController {

    @Autowired
    private CourseConverter courseConverter;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/api/course")
    public ResponseEntity<CourseJson> createCourse(@Valid @RequestBody RequestCourseJson requestCourseJson) {
        Course course = courseConverter.convertToEntity(requestCourseJson);
        course = courseRepository.save(course);
        return ResponseEntity.ok(courseConverter.convertToJson(course));
    }

    @GetMapping("/api/course")
    public ResponseEntity<PageJson<CourseJson>> getCourse(Pageable pageable) {
        CourseConverter.checkPageable(pageable);
        return ResponseEntity.ok(courseConverter.convertToPageJson(courseRepository.findAll(pageable)));
    }

    @GetMapping("/api/course/{uuid}")
    public ResponseEntity<CourseJson> getCourse(@PathVariable UUID uuid) {
        Optional<Course> courseOptional = courseRepository.findByUuid(uuid);
        return courseOptional.map(value -> ResponseEntity.ok(courseConverter.convertToJson(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/api/course/{uuid}")
    public ResponseEntity<CourseJson> putCourse(@PathVariable UUID uuid,
                                                @Valid @RequestBody RequestCourseJson requestCourseJson) {
        Optional<Course> courseOptional = courseRepository.findByUuid(uuid);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Course course = courseOptional.get();
        courseConverter.updateEntity(course, requestCourseJson);
        course = courseRepository.save(course);
        return ResponseEntity.ok(courseConverter.convertToJson(course));
    }

    @DeleteMapping("/api/course/{uuid}")
    public ResponseEntity<CourseJson> deleteCourse(@PathVariable UUID uuid) {
        Optional<Course> courseOptional = courseRepository.findByUuid(uuid);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Course course = courseOptional.get();
        courseRepository.delete(course);
        return ResponseEntity.ok(courseConverter.convertToJson(course));
    }
}
