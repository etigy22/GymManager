package com.gymmanager.repository;

import com.gymmanager.model.Member;
import com.gymmanager.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentRepositoryImpl implements EnrollmentRepository {

    @Override
    public boolean isEnrolled(int memberId, int courseId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM enrollments WHERE member_id = ? AND course_id = ?")) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, courseId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking enrollment: " + e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void enrollMember(int memberId, int courseId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO enrollments (member_id, course_id) VALUES (?, ?)")) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, courseId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error enrolling member: " + e.getMessage(), e);
        }
    }

    @Override
    public void removeMember(int memberId, int courseId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM enrollments WHERE member_id = ? AND course_id = ?")) {

            stmt.setInt(1, memberId);
            stmt.setInt(2, courseId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Member was not enrolled in this course");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error removing member from course: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Member> getCourseMembers(int courseId, MemberRepository memberRepository) {
        List<Member> members = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT member_id FROM enrollments WHERE course_id = ?")) {

            stmt.setInt(1, courseId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int memberId = rs.getInt("member_id");
                Member member = memberRepository.findById(memberId);
                if (member != null) {
                    members.add(member);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listing course members: " + e.getMessage(), e);
        }
        return members;
    }
}