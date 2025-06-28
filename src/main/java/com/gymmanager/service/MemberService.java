package com.gymmanager.service;

import com.gymmanager.model.Member;

import java.util.List;
/**
 * Interface for managing enrollments in gym courses.
 * Provides methods to enroll or remove members from courses and retrieve course members.
 */
public interface MemberService {
    List<Member> getAllMembers();

    Member getMemberById(int id);

    void addMember(String firstName, String lastName, String membershipType);

    void updateMember(int id, String firstName, String lastName, String membershipType);

    void removeMember(int id);

}
