package com.gymmanager.repository;

import com.gymmanager.model.GymClass;
import com.gymmanager.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassRepositoryImpl implements ClassRepository {

    @Override
    public List<GymClass> findAll() {
        List<GymClass> classes = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM gym_classes")) {

            while (rs.next()) {
                GymClass gymClass = new GymClass(
                        rs.getInt("class_id"),
                        rs.getString("name"),
                        rs.getString("instructor_first_name"),
                        rs.getString("instructor_last_name"),
                        rs.getInt("max_capacity")
                );
                classes.add(gymClass);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listing classes: " + e.getMessage(), e);
        }
        return classes;
    }

    @Override
    public GymClass findById(int classId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gym_classes WHERE class_id = ?")) {

            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new GymClass(
                        rs.getInt("class_id"),
                        rs.getString("name"),
                        rs.getString("instructor_first_name"),
                        rs.getString("instructor_last_name"),
                        rs.getInt("max_capacity")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding class: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void save(GymClass gymClass) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO gym_classes (name, instructor_first_name, instructor_last_name, max_capacity) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, gymClass.getName());
            stmt.setString(2, gymClass.getInstructorFirstName());
            stmt.setString(3, gymClass.getInstructorLastName());
            stmt.setInt(4, gymClass.getMaxCapacity());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding class: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(int classId, String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE gym_classes SET name = ?, instructor_first_name = ?, instructor_last_name = ?, max_capacity = ? WHERE class_id = ?")) {

            stmt.setString(1, name);
            stmt.setString(2, instructorFirstName);
            stmt.setString(3, instructorLastName);
            stmt.setInt(4, maxCapacity);
            stmt.setInt(5, classId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No class found with ID: " + classId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating class: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int classId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM gym_classes WHERE class_id = ?")) {

            stmt.setInt(1, classId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("No class found with ID: " + classId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error removing class: " + e.getMessage(), e);
        }
    }

    @Override
    public int getEnrollmentCount(int classId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM enrollments WHERE class_id = ?")) {

            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting enrollment count: " + e.getMessage(), e);
        }
        return 0;
    }
}