package com.gymmanager.repository;

import com.gymmanager.model.Member;
import com.gymmanager.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberRepositoryImpl implements MemberRepository {

    @Override
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM members")) {

            while (rs.next()) {
                Member member = new Member(
                        rs.getInt("member_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("membership_type")
                );
                members.add(member);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listing members: " + e.getMessage(), e);
        }
        return members;
    }

    @Override
    public Member findById(int memberId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM members WHERE member_id = ?")) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Member(
                        rs.getInt("member_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("membership_type")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding member: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean existsByName(String firstName, String lastName) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) FROM members WHERE first_name = ? AND last_name = ?")) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking if member exists: " + e.getMessage(), e);
        }
        return false;
    }

    @Override
    public void save(Member member) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO members (first_name, last_name, membership_type) VALUES (?, ?, ?)")) {

            stmt.setString(1, member.getFirstName());
            stmt.setString(2, member.getLastName());
            stmt.setString(3, member.getMembershipType());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding member: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(int memberId, String firstName, String lastName, String membershipType) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE members SET first_name = ?, last_name = ?, membership_type = ? WHERE member_id = ?")) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, membershipType);
            stmt.setInt(4, memberId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No member found with ID: " + memberId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating member: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(int memberId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM members WHERE member_id = ?")) {

            stmt.setInt(1, memberId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("No member found with ID: " + memberId);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error removing member: " + e.getMessage(), e);
        }
    }
}