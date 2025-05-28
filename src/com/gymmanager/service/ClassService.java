package com.gymmanager.service;

import com.gymmanager.model.GymClass;

import java.util.List;

public interface ClassService {
    List<GymClass> getAllClasses();

    GymClass getClassById(int id);

    void addClass(String name, String instructorFirstName, String instructorLastName, int maxCapacity);

    void updateClass(int id, String name, String instructorFirstName, String instructorLastName, int maxCapacity);

    void removeClass(int id);

    int getEnrollmentCount(int classId);

}
