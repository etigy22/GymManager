package com.gymmanager.repository;

import com.gymmanager.model.Member;

import java.util.List;

public interface EnrollmentRepository {
    boolean isEnrolled(int memberId, int classId);

    void enrollMember(int memberId, int classId);

    void removeMember(int memberId, int classId);

    List<Member> getClassMembers(int classId, MemberRepository memberRepository);
}
