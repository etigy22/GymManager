package com.gymmanager.service;

import com.gymmanager.model.Member;

import java.util.List;

public interface EnrollmentService {
    void enrollMemberInCourse(int memberId, int classId);

    void removeMemberFromCourse(int memberId, int classId);

    List<Member> getCourseMembers(int classId);

    List<Member> getAllMembers();
}
