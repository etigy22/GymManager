package com.gymmanager.service;

import com.gymmanager.exception.*;
import com.gymmanager.model.GymCourse;
import com.gymmanager.model.Member;
import com.gymmanager.repository.CourseRepository;
import com.gymmanager.repository.EnrollmentRepository;
import com.gymmanager.repository.MemberRepository;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentServiceImplTest {

    static class FakeMemberRepository implements MemberRepository {
        final Map<Integer, Member> members = new HashMap<>();
        int idSeq = 1;

        @Override
        public List<Member> findAll() {
            return new ArrayList<>(members.values());
        }

        @Override
        public Member findById(int id) {
            return members.get(id);
        }

        @Override
        public void save(Member member) {
            Member saved = new Member(idSeq++, member.getFirstName(), member.getLastName(), member.getMembershipType());
            members.put(saved.getMemberId(), saved);
        }

        @Override
        public void update(int id, String firstName, String lastName, String membershipType) {
            if (members.containsKey(id)) {
                members.put(id, new Member(id, firstName, lastName, membershipType));
            }
        }

        @Override
        public void delete(int id) {
            members.remove(id);
        }

        @Override
        public boolean existsByName(String firstName, String lastName) {
            return members.values().stream()
                    .anyMatch(m -> m.getFirstName().equals(firstName) && m.getLastName().equals(lastName));
        }
    }

    static class FakeCourseRepository implements CourseRepository {
        final Map<Integer, GymCourse> courses = new HashMap<>();
        int idSeq = 1;
        int enrollmentCount = 0;

        @Override
        public List<GymCourse> findAll() {
            return new ArrayList<>(courses.values());
        }

        @Override
        public GymCourse findById(int id) {
            return courses.get(id);
        }

        @Override
        public void save(GymCourse gymCourse) {
            GymCourse saved = new GymCourse(idSeq++, gymCourse.getName(),
                    gymCourse.getInstructorFirstName(),
                    gymCourse.getInstructorLastName(),
                    gymCourse.getMaxCapacity());
            courses.put(saved.getCourseId(), saved);
        }

        @Override
        public void update(int id, String name, String instructorFirstName, String instructorLastName, int maxCapacity) {
            if (courses.containsKey(id)) {
                courses.put(id, new GymCourse(id, name, instructorFirstName, instructorLastName, maxCapacity));
            }
        }

        @Override
        public void delete(int id) {
            courses.remove(id);
        }

        @Override
        public int getEnrollmentCount(int courseId) {
            return enrollmentCount;
        }
    }

    static class FakeEnrollmentRepository implements EnrollmentRepository {
        final Set<String> enrollments = new HashSet<>();

        @Override
        public boolean isEnrolled(int memberId, int courseId) {
            return enrollments.contains(memberId + "-" + courseId);
        }

        @Override
        public void enrollMember(int memberId, int courseId) {
            enrollments.add(memberId + "-" + courseId);
        }

        @Override
        public void removeMember(int memberId, int courseId) {
            enrollments.remove(memberId + "-" + courseId);
        }

        @Override
        public List<Member> getCourseMembers(int courseId, MemberRepository memberRepository) {
            List<Member> result = new ArrayList<>();
            for (String key : enrollments) {
                String[] parts = key.split("-");
                int memberId = Integer.parseInt(parts[0]);
                int cId = Integer.parseInt(parts[1]);
                if (cId == courseId) {
                    Member m = memberRepository.findById(memberId);
                    if (m != null) result.add(m);
                }
            }
            return result;
        }
    }

    private FakeMemberRepository memberRepository;
    private FakeCourseRepository courseRepository;
    private FakeEnrollmentRepository enrollmentRepository;
    private EnrollmentServiceImpl service;

    @BeforeEach
    void setUp() {
        memberRepository = new FakeMemberRepository();
        courseRepository = new FakeCourseRepository();
        enrollmentRepository = new FakeEnrollmentRepository();
        service = new EnrollmentServiceImpl(enrollmentRepository, memberRepository, courseRepository);
    }

    @AfterEach
    void tearDown() {
        memberRepository = null;
        courseRepository = null;
        enrollmentRepository = null;
        service = null;
    }

    @Test
    void enrollMemberInCourse() {
        memberRepository.save(new Member(0, "Anna", "Smith", "Premium"));
        courseRepository.save(new GymCourse(0, "Yoga", "Anna", "Smith", 2));
        int memberId = memberRepository.members.keySet().iterator().next();
        int courseId = courseRepository.courses.keySet().iterator().next();

        // Success
        service.enrollMemberInCourse(memberId, courseId);
        assertTrue(enrollmentRepository.isEnrolled(memberId, courseId));

        // Duplicate enrollment
        assertThrows(EnrollmentException.DuplicateEnrollmentException.class, () ->
                service.enrollMemberInCourse(memberId, courseId));

        // Not premium
        memberRepository.save(new Member(0, "John", "Doe", "Standard"));
        int nonPremiumId = memberRepository.members.keySet()
                .stream()
                .max(Integer::compareTo)
                .orElseThrow(() -> new AssertionError("No member found"));
        assertThrows(EnrollmentException.EnrollmentNotAllowedException.class, () ->
                service.enrollMemberInCourse(nonPremiumId, courseId));

        // Non-existent member
        assertThrows(MemberException.MemberNotFoundException.class, () ->
                service.enrollMemberInCourse(999, courseId));

        // Non-existent course
        assertThrows(CourseException.CourseNotFoundException.class, () ->
                service.enrollMemberInCourse(memberId, 999));

        // Course full
        courseRepository.enrollmentCount = 2;
        memberRepository.save(new Member(0, "Jane", "Roe", "Premium"));
        int newMemberId = memberRepository.members.keySet()
                .stream()
                .max(Integer::compareTo)
                .orElseThrow(() -> new AssertionError("No member found"));
        assertThrows(CourseException.CourseFullException.class, () ->
                service.enrollMemberInCourse(newMemberId, courseId));
    }

    @Test
    void removeMemberFromCourse() {
        memberRepository.save(new Member(0, "Anna", "Smith", "Premium"));
        courseRepository.save(new GymCourse(0, "Yoga", "Anna", "Smith", 2));
        int memberId = memberRepository.members.keySet().iterator().next();
        int courseId = courseRepository.courses.keySet().iterator().next();

        enrollmentRepository.enrollMember(memberId, courseId);
        service.removeMemberFromCourse(memberId, courseId);
        assertFalse(enrollmentRepository.isEnrolled(memberId, courseId));

        // Non-existent member
        assertThrows(MemberException.MemberNotFoundException.class, () ->
                service.removeMemberFromCourse(999, courseId));

        // Non-existent course
        assertThrows(CourseException.CourseNotFoundException.class, () ->
                service.removeMemberFromCourse(memberId, 999));
    }

    @Test
    void getCourseMembers() {
        memberRepository.save(new Member(0, "Anna", "Smith", "Premium"));
        memberRepository.save(new Member(0, "John", "Doe", "Premium"));
        courseRepository.save(new GymCourse(0, "Yoga", "Anna", "Smith", 2));
        int courseId = courseRepository.courses.keySet().iterator().next();
        Iterator<Integer> memberIds = memberRepository.members.keySet().iterator();
        int memberId1 = memberIds.next();
        int memberId2 = memberIds.next();

        enrollmentRepository.enrollMember(memberId1, courseId);
        enrollmentRepository.enrollMember(memberId2, courseId);

        List<Member> members = service.getCourseMembers(courseId);
        assertEquals(2, members.size());

        // Non-existent course
        assertThrows(CourseException.CourseNotFoundException.class, () ->
                service.getCourseMembers(999));
    }

    @Test
    void getAllMembers() {
        memberRepository.save(new Member(0, "Anna", "Smith", "Premium"));
        memberRepository.save(new Member(0, "John", "Doe", "Standard"));
        List<Member> all = service.getAllMembers();
        assertEquals(2, all.size());
    }
}