package com.gymmanager.service;

import com.gymmanager.exception.ClassException;
import com.gymmanager.model.GymClass;
import com.gymmanager.repository.ClassRepository;

import java.util.List;

public class ClassServiceImpl implements ClassService {
    private final ClassRepository repository;

    public ClassServiceImpl(ClassRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GymClass> getAllClasses() {
        return repository.findAll();
    }

    @Override
    public GymClass getClassById(int id) {
        GymClass gymClass = repository.findById(id);
        if (gymClass == null) {
            throw new ClassException.ClassNotFoundException(id);
        }
        return gymClass;
    }

    @Override
    public void addClass(String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        validateClassData(name, instructorFirstName, instructorLastName, maxCapacity);
        GymClass gymClass = new GymClass(0, name, instructorFirstName, instructorLastName, maxCapacity);
        repository.save(gymClass);
    }

    @Override
    public void updateClass(int id, String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        validateClassData(name, instructorFirstName, instructorLastName, maxCapacity);
        getClassById(id);

        repository.update(id, name, instructorFirstName, instructorLastName, maxCapacity);
    }

    @Override
    public void removeClass(int id) {
        getClassById(id);

        repository.delete(id);
    }

    @Override
    public int getEnrollmentCount(int classId) {
        getClassById(classId);

        return repository.getEnrollmentCount(classId);
    }

    private void validateClassData(String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        if (name == null || name.trim().isEmpty()) {
            throw new ClassException.InvalidClassDataException("Class name cannot be empty");
        }
        if (instructorFirstName == null || instructorFirstName.trim().isEmpty()) {
            throw new ClassException.InvalidClassDataException("Instructor first name cannot be empty");
        }
        if (instructorLastName == null || instructorLastName.trim().isEmpty()) {
            throw new ClassException.InvalidClassDataException("Instructor last name cannot be empty");
        }
        if (maxCapacity <= 0) {
            throw new ClassException.InvalidClassDataException("Maximum capacity must be greater than zero");
        }
    }
}