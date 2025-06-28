package com.gymmanager.repository;

import com.gymmanager.model.Member;

import java.util.List;
/**
 * Interface for managing enrollments in gym courses.
 * Provides methods to check enrollment status, enroll or remove members, and retrieve course members.
 */
public interface EnrollmentRepository {
    boolean isEnrolled(int memberId, int courseId);

    void enrollMember(int memberId, int courseId);

    void removeMember(int memberId, int courseId);

    List<Member> getCourseMembers(int courseId, MemberRepository memberRepository);
}
