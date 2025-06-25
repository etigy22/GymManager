package com.gymmanager.service;

import com.gymmanager.exception.CourseException;
import com.gymmanager.model.GymCourse;
import com.gymmanager.repository.CourseRepository;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    private final CourseRepository repository;

    public CourseServiceImpl(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GymCourse> getAllCourses() {
        return repository.findAll();
    }

    @Override
    public GymCourse getCourseById(int id) {
        GymCourse gymCourse = repository.findById(id);
        if (gymCourse == null) {
            throw new CourseException.CourseNotFoundException(id);
        }
        return gymCourse;
    }

    @Override
    public void addCourse(String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        validateCourseData(name, instructorFirstName, instructorLastName, maxCapacity);
        GymCourse gymCourse = new GymCourse(0, name, instructorFirstName, instructorLastName, maxCapacity);
        repository.save(gymCourse);
    }

    @Override
    public void updateCourse(int id, String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        validateCourseData(name, instructorFirstName, instructorLastName, maxCapacity);
        getCourseById(id);

        repository.update(id, name, instructorFirstName, instructorLastName, maxCapacity);
    }

    @Override
    public void removeCourse(int id) {
        getCourseById(id);

        repository.delete(id);
    }

    @Override
    public int getEnrollmentCount(int courseId) {
        getCourseById(courseId);

        return repository.getEnrollmentCount(courseId);
    }

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