package com.pluralsight.cli.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.pluralsight.courseinfo.domain.Course;
import com.pluralsight.courseinfo.repository.CourseRepository;



public class CourseStorageServiceTest {
    @Test
    void testStorePlurialsightCourses() {
        CourseRepository courseRepository = new InMemoryCourseRepository();
        CourseStorageService courseStorageService = new CourseStorageService(courseRepository);

        PluralsightCourse psCourse1 = new PluralsightCourse("1",
        "Title 1",
        "01:40:00.123", 
        "/url-1", false);
        courseStorageService.storePlurialsightCourses(List.of(psCourse1));
        
        Course expectedCourse = new Course("1", "Title 1", 100, "https://app.pluralsight.com/url-1", Optional.empty());

        assertEquals(List.of(expectedCourse),  courseRepository.getAllCourses());    

    }

    static class InMemoryCourseRepository implements CourseRepository {
        private List<Course> courses = new ArrayList<>();

        @Override
        public void saveCourse(Course course)  {
           courses.add(course);
        }

        @Override
        public List<Course> getAllCourses() {
           return courses;
        }

        @Override
        public void addNotes(String id, String notes) {
            throw new UnsupportedOperationException();
        }


    }
}
