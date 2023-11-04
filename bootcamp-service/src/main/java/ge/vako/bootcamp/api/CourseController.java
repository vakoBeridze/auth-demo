package ge.vako.bootcamp.api;

import ge.vako.bootcamp.domain.Course;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final List<Course> courses = new ArrayList<>();

    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        courses.add(course);
        return course;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courses;
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable int id) {
        if (id >= 0 && id < courses.size()) {
            return courses.get(id);
        } else {
            return null;
        }
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable int id, @RequestBody Course updatedCourse) {
        if (id >= 0 && id < courses.size()) {
            courses.set(id, updatedCourse);
            return updatedCourse;
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public Course deleteCourse(@PathVariable int id) {
        if (id >= 0 && id < courses.size()) {
            Course removedCourse = courses.get(id);
            courses.remove(id);
            return removedCourse;
        } else {
            return null;
        }
    }
}

