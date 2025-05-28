package com.gymmanager.service;

import com.gymmanager.model.Member;

import java.util.List;

public interface EnrollmentService {
    void enrollMemberInClass(int memberId, int classId);

    void removeMemberFromClass(int memberId, int classId);

    List<Member> getClassMembers(int classId);

    List<Member> getAllMembers();
}
