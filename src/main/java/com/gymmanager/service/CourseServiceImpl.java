package com.gymmanager.service;

import com.gymmanager.exception.CourseException;
import com.gymmanager.model.GymCourse;
import com.gymmanager.repository.CourseRepository;

import java.util.List;
/**
 * Implementation of the CourseService interface for managing gym courses.
 * Provides methods to perform CRUD operations on GymCourse entities.
 */
public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;
    /**
     * Constructor for CourseServiceImpl.
     * Initializes the repository used for course management.
     *
     * @param repository the CourseRepository instance to be used
     */
    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }
    /** Retrieves all gym courses from the repository. */
    @Override
    public List<GymCourse> getAllCourses() {
        return repository.findAll();
    }
    /** Retrieves a gym course by its ID. */
    @Override
    public GymCourse getCourseById(int id) {
        GymCourse gymCourse = repository.findById(id);
        if (gymCourse == null) {
            throw new CourseException.CourseNotFoundException(id);
        }
        return gymCourse;
    }
    /** Adds a new gym course with the specified details. */
    @Override
    public void addCourse(String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        validateCourseData(name, instructorFirstName, instructorLastName, maxCapacity);
        GymCourse gymCourse = new GymCourse(0, name, instructorFirstName, instructorLastName, maxCapacity);
        repository.save(gymCourse);
    }
    /** Updates an existing gym course with the specified ID and details. */
    @Override
    public void updateCourse(int id, String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        validateCourseData(name, instructorFirstName, instructorLastName, maxCapacity);
        getCourseById(id);

        repository.update(id, name, instructorFirstName, instructorLastName, maxCapacity);
    }
    /** Removes a gym course by its ID. */
    @Override
    public void removeCourse(int id) {
        getCourseById(id);

        repository.delete(id);
    }
    /** Retrieves the number of enrollments for a specific course by its ID. */
    @Override
    public int getEnrollmentCount(int courseId) {
        getCourseById(courseId);

        return repository.getEnrollmentCount(courseId);
    }
    /** Validates the course data before adding or updating a course. */
    private void validateCourseData(String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        if (name == null || name.trim().isEmpty()) {
            throw new CourseException.InvalidCourseDataException("Course name cannot be empty");
        }
        if (instructorFirstName == null || instructorFirstName.trim().isEmpty()) {
            throw new CourseException.InvalidCourseDataException("Instructor first name cannot be empty");
        }
        if (instructorLastName == null || instructorLastName.trim().isEmpty()) {
            throw new CourseException.InvalidCourseDataException("Instructor last name cannot be empty");
        }
        if (maxCapacity <= 0) {
            throw new CourseException.InvalidCourseDataException("Maximum capacity must be greater than zero");
        }
    }
}