package com.gymmanager.service;

import com.gymmanager.exception.CourseException;
import com.gymmanager.model.GymCourse;
import com.gymmanager.repository.CourseRepository;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceImplTest {

    static class FakeCourseRepository implements CourseRepository {
        private final Map<Integer, GymCourse> courses = new HashMap<>();
        private int idSeq = 1;
        int enrollmentCount = 0;

        @Override
        public List<GymCourse> findAll() {
            return new ArrayList<>(courses.values());
        }

        @Override
        public GymCourse findById(int id) {
            return courses.get(id);
        }

        @Override
        public void save(GymCourse gymCourse) {
            // Simulate DB auto-increment ID assignment
            GymCourse saved = new GymCourse(idSeq++, gymCourse.getName(),
                    gymCourse.getInstructorFirstName(),
                    gymCourse.getInstructorLastName(),
                    gymCourse.getMaxCapacity());
            courses.put(saved.getCourseId(), saved);
        }

        @Override
        public void update(int id, String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
            GymCourse existing = courses.get(id);
            if (existing != null) {
                GymCourse updated = new GymCourse(id, name, instructorFirstName, instructorLastName, maxCapacity);
                courses.put(id, updated);
            }
        }

        @Override
        public void delete(int id) {
            courses.remove(id);
        }

        @Override
        public int getEnrollmentCount(int courseId) {
            return enrollmentCount;
        }
    }

    private FakeCourseRepository repository;
    private CourseServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = new FakeCourseRepository();
        service = new CourseServiceImpl(repository);
    }

    @AfterEach
    void tearDown() {
        repository = null;
        service = null;
    }

    @Test
    void getAllCourses() {
        repository.save(new GymCourse(0, "Yoga", "Anna", "Smith", 10));
        repository.save(new GymCourse(0, "Pilates", "John", "Doe", 12));
        List<GymCourse> all = service.getAllCourses();
        assertEquals(2, all.size());
    }

    @Test
    void getCourseById() {
        repository.save(new GymCourse(0, "Yoga", "Anna", "Smith", 10));
        int id = repository.courses.keySet().iterator().next();
        GymCourse found = service.getCourseById(id);
        assertEquals("Yoga", found.getName());
        assertThrows(CourseException.CourseNotFoundException.class, () -> service.getCourseById(999));
    }

    @Test
    void addCourse() {
        service.addCourse("Spin", "Mike", "Lee", 15);
        assertEquals(1, repository.courses.size());
        GymCourse c = repository.courses.values().iterator().next();
        assertEquals("Spin", c.getName());
        assertThrows(CourseException.InvalidCourseDataException.class, () -> service.addCourse("", "A", "B", 10));
    }

    @Test
    void updateCourse() {
        repository.save(new GymCourse(0, "Yoga", "Anna", "Smith", 10));
        int id = repository.courses.keySet().iterator().next();
        service.updateCourse(id, "Pilates", "John", "Doe", 12);
        GymCourse updated = repository.findById(id);
        assertEquals("Pilates", updated.getName());
        assertThrows(CourseException.CourseNotFoundException.class, () ->
                service.updateCourse(999, "Pilates", "John", "Doe", 12));
    }

    @Test
    void removeCourse() {
        repository.save(new GymCourse(0, "Yoga", "Anna", "Smith", 10));
        int id = repository.courses.keySet().iterator().next();
        service.removeCourse(id);
        assertNull(repository.findById(id));
        assertThrows(CourseException.CourseNotFoundException.class, () -> service.removeCourse(999));
    }

    @Test
    void getEnrollmentCount() {
        repository.save(new GymCourse(0, "Yoga", "Anna", "Smith", 10));
        int id = repository.courses.keySet().iterator().next();
        repository.enrollmentCount = 5;
        assertEquals(5, service.getEnrollmentCount(id));
        assertThrows(CourseException.CourseNotFoundException.class, () -> service.getEnrollmentCount(999));
    }
}