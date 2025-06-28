package com.gymmanager.service;

import com.gymmanager.exception.*;
import com.gymmanager.model.GymCourse;
import com.gymmanager.model.Member;
import com.gymmanager.repository.*;

import java.util.List;
/**
 * Implementation of the EnrollmentService interface.
 * Handles the business logic for enrolling members in gym courses,
 * removing members from courses, and retrieving course members.
 */
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    /** Constructor for EnrollmentServiceImpl. */
    public EnrollmentServiceImpl(
            EnrollmentRepository enrollmentRepository,
            MemberRepository memberRepository,
            CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.memberRepository = memberRepository;
        this.courseRepository = courseRepository;
    }
    /** Enrolls a member in a gym course if they meet the requirements. */
    @Override
    public void enrollMemberInCourse(int memberId, int courseId) {
        // Check if member exists
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new MemberException.MemberNotFoundException(memberId);
        }

        // Check membership type
        if (!member.getMembershipType().equalsIgnoreCase("Premium")) {
            throw new EnrollmentException.EnrollmentNotAllowedException();
        }

        // Check if course exists
        GymCourse gymCourse = courseRepository.findById(courseId);
        if (gymCourse == null) {
            throw new CourseException.CourseNotFoundException(courseId);
        }

        // Check if already enrolled
        if (enrollmentRepository.isEnrolled(memberId, courseId)) {
            throw new EnrollmentException.DuplicateEnrollmentException(memberId, courseId);
        }

        // Check course capacity
        int currentEnrollment = courseRepository.getEnrollmentCount(courseId);
        if (currentEnrollment >= gymCourse.getMaxCapacity()) {
            throw new CourseException.CourseFullException(courseId);
        }

        // Enroll member
        enrollmentRepository.enrollMember(memberId, courseId);
    }
    /** Removes a member from a gym course if they are enrolled. */
    @Override
    public void removeMemberFromCourse(int memberId, int courseId) {
        // Check if member exists
        Member member = memberRepository.findById(memberId);
        if (member == null) {
            throw new MemberException.MemberNotFoundException(memberId);
        }

        // Check if course exists
        GymCourse gymCourse = courseRepository.findById(courseId);
        if (gymCourse == null) {
            throw new CourseException.CourseNotFoundException(courseId);
        }

        // Remove enrollment
        enrollmentRepository.removeMember(memberId, courseId);
    }
    /** Retrieves a list of members enrolled in a specific course. */
    @Override
    public List<Member> getCourseMembers(int courseId) {
        // Check if course exists
        GymCourse gymCourse = courseRepository.findById(courseId);
        if (gymCourse == null) {
            throw new CourseException.CourseNotFoundException(courseId);
        }

        return enrollmentRepository.getCourseMembers(courseId, memberRepository);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}