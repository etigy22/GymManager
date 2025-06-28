package com.gymmanager.service;

import com.gymmanager.model.Member;

import java.util.List;
/**
 * Interface for managing enrollments in gym courses.
 * Provides methods to enroll or remove members from courses and retrieve course members.
 */
public interface EnrollmentService {
    void enrollMemberInCourse(int memberId, int classId);

    void removeMemberFromCourse(int memberId, int classId);

    List<Member> getCourseMembers(int classId);

    List<Member> getAllMembers();
}
