package com.gymmanager.repository;

import com.gymmanager.model.GymCourse;

import java.util.List;

public interface CourseRepository {
    List<GymCourse> findAll();

    GymCourse findById(int courseId);

    void save(GymCourse gymCourse);

    void update(int courseId, String name, String instructorFirstName, String instructorLastName, int maxCapacity);

    void delete(int courseId);

    int getEnrollmentCount(int courseId);
}
