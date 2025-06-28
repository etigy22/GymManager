package com.gymmanager.repository;

import com.gymmanager.model.GymCourse;
import com.gymmanager.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementation of CourseRepository for managing gym courses in the repository.
 * Provides methods to perform CRUD operations on GymCourse entities.
 */
public class CourseRepositoryImpl implements CourseRepository {
    /** Default constructor. */
    @Override
    public List<GymCourse> findAll() {
        List<GymCourse> courses = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM gym_courses")) {

            while (rs.next()) {
                GymCourse gymCourse = new GymCourse(
                        rs.getInt("course_id"),
                        rs.getString("name"),
                        rs.getString("instructor_first_name"),
                        rs.getString("instructor_last_name"),
                        rs.getInt("max_capacity")
                );
                courses.add(gymCourse);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listing courses: " + e.getMessage(), e);
        }
        return courses;
    }
    /** Finds a gym course by its ID. */
    @Override
    public GymCourse findById(int courseId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM gym_courses WHERE course_id = ?")) {

            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new GymCourse(
                        rs.getInt("course_id"),
                        rs.getString("name"),
                        rs.getString("instructor_first_name"),
                        rs.getString("instructor_last_name"),
                        rs.getInt("max_capacity")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding course: " + e.getMessage(), e);
        }
        return null;
    }
    /** Saves a new gym course to the repository.*/
    @Override
    public void save(GymCourse gymCourse) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO gym_courses (name, instructor_first_name, instructor_last_name, max_capacity) VALUES (?, ?, ?, ?)")) {

            stmt.setString(1, gymCourse.getName());
            stmt.setString(2, gymCourse.getInstructorFirstName());
            stmt.setString(3, gymCourse.getInstructorLastName());
            stmt.setInt(4, gymCourse.getMaxCapacity());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding course: " + e.getMessage(), e);
        }
    }
    /** Updates an existing gym course in the repository. */
    @Override
    public void update(int courseId, String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE gym_courses SET name = ?, instructor_first_name = ?, instructor_last_name = ?, max_capacity = ? WHERE course_id = ?")) {

            stmt.setString(1, name);
            stmt.setString(2, instructorFirstName);
            stmt.setString(3, instructorLastName);
            stmt.setInt(4, maxCapacity);
            stmt.setInt(5, courseId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No course found with ID: " + courseId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating course: " + e.getMessage(), e);
        }
    }
    /** Deletes a gym course from the repository. */
    @Override
    public void delete(int courseId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM gym_courses WHERE course_id = ?")) {

            stmt.setInt(1, courseId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("No course found with ID: " + courseId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error removing course: " + e.getMessage(), e);
        }
    }
    /** Gets the number of enrollments for a specific course. */
    @Override
    public int getEnrollmentCount(int courseId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM enrollments WHERE course_id = ?")) {

            stmt.setInt(1, courseId);
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