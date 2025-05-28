package com.gymmanager.service;

import com.gymmanager.exception.ClassException;
import com.gymmanager.exception.EnrollmentException;
import com.gymmanager.exception.MemberException;
import com.gymmanager.model.GymClass;
import com.gymmanager.model.Member;
import com.gymmanager.repository.EnrollmentRepository;
import com.gymmanager.repository.ClassRepository;
import com.gymmanager.repository.MemberRepository;

import java.util.List;

public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final MemberRepository memberRepository;
    private final ClassRepository classRepository;

    public EnrollmentServiceImpl(
            EnrollmentRepository enrollmentRepository,
            MemberRepository memberRepository,
            ClassRepository classRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.memberRepository = memberRepository;
        this.classRepository = classRepository;
    }

    @Override
    public void enrollMemberInClass(int memberId, int classId) {
        // Check if member exists
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new MemberException.MemberNotFoundException(memberId);
        }

        // Check membership type
        if (!member.getMembershipType().equalsIgnoreCase("Premium")) {
            throw new EnrollmentException.EnrollmentNotAllowedException();
        }

        // Check if class exists
        GymClass gymClass = classRepository.findById(classId);
        if (gymClass == null) {
            throw new ClassException.ClassNotFoundException(classId);
        }

        // Check if already enrolled
        if (enrollmentRepository.isEnrolled(memberId, classId)) {
            throw new EnrollmentException.DuplicateEnrollmentException(memberId, classId);
        }

        // Check class capacity
        int currentEnrollment = classRepository.getEnrollmentCount(classId);
        if (currentEnrollment >= gymClass.getMaxCapacity()) {
            throw new ClassException.ClassFullException(classId);
        }

        // Enroll member
        enrollmentRepository.enrollMember(memberId, classId);
    }

    @Override
    public void removeMemberFromClass(int memberId, int classId) {
        // Check if member exists
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new MemberException.MemberNotFoundException(memberId);
        }

        // Check if class exists
        GymClass gymClass = classRepository.findById(classId);
        if (gymClass == null) {
            throw new ClassException.ClassNotFoundException(classId);
        }

        // Remove enrollment
        enrollmentRepository.removeMember(memberId, classId);
    }

    @Override
    public List<Member> getClassMembers(int classId) {
        // Check if class exists
        GymClass gymClass = classRepository.findById(classId);
        if (gymClass == null) {
            throw new ClassException.ClassNotFoundException(classId);
        }

        return enrollmentRepository.getClassMembers(classId, memberRepository);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}