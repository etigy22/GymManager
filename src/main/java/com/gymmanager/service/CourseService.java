package com.gymmanager.service;

import com.gymmanager.model.GymCourse;

import java.util.List;

public interface CourseService {
    List<GymCourse> getAllCourses();

    GymCourse getCourseById(int id);

    void addCourse(String name, String instructorFirstName, String instructorLastName, int maxCapacity);

    void updateCourse(int id, String name, String instructorFirstName, String instructorLastName, int maxCapacity);

    void removeCourse(int id);

    int getEnrollmentCount(int courseId);

}
