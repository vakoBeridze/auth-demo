package ge.vako.bootcamp.api;

import ge.vako.bootcamp.domain.Course;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final List<Course> courses;

    public CourseController() {
        this.courses = new ArrayList<>();
        this.courses.addAll(List.of(
                new Course(1, "Angular"),
                new Course(2, "Java"),
                new Course(3, "React"),
                new Course(4, "Java 2")
        ));
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courses;
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id) {
        return fetchCourse(id);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        courses.add(course);
        return course;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable int id, @RequestBody Course updatedCourse) {
        Course courseToUpdate = fetchCourse(id);
        courseToUpdate.setName(updatedCourse.getName());
        return courseToUpdate;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/{id}")
    public Course deleteCourse(@PathVariable int id) {
        Course courseToDelete = fetchCourse(id);
        courses.remove(courseToDelete);
        return courseToDelete;
    }


    private Course fetchCourse(int id) {
        return courses.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course could not be found"));
    }
}

