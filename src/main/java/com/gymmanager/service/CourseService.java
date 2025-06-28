package com.gymmanager.service;

import com.gymmanager.model.GymCourse;

import java.util.List;
/**
 * Interface for managing gym courses.
 * Provides methods to perform CRUD operations on GymCourse entities.
 */
public interface CourseService {
    List<GymCourse> getAllCourses();

    GymCourse getCourseById(int id);

    void addCourse(String name, String instructorFirstName, String instructorLastName, int maxCapacity);

    void updateCourse(int id, String name, String instructorFirstName, String instructorLastName, int maxCapacity);

    void removeCourse(int id);

    int getEnrollmentCount(int courseId);

}
