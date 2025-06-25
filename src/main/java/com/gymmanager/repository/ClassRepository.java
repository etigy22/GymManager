package com.gymmanager.repository;

import com.gymmanager.model.GymClass;

import java.util.List;

public interface ClassRepository {
    List<GymClass> findAll();

    GymClass findById(int classId);

    void save(GymClass gymClass);

    void update(int classId, String name, String instructorFirstName, String instructorLastName, int maxCapacity);

    void delete(int classId);

    int getEnrollmentCount(int classId);
}
