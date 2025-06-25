package com.gymmanager.service;

import com.gymmanager.exception.MemberException;
import com.gymmanager.model.Member;
import com.gymmanager.repository.MemberRepository;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceImplTest {

    static class FakeMemberRepository implements MemberRepository {
        private final Map<Integer, Member> members = new HashMap<>();
        private int idSeq = 1;

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

    private FakeMemberRepository repository;
    private MemberServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = new FakeMemberRepository();
        service = new MemberServiceImpl(repository);
    }

    @AfterEach
    void tearDown() {
        repository = null;
        service = null;
    }

    @Test
    void getAllMembers() {
        repository.save(new Member(0, "Anna", "Smith", "Standard"));
        repository.save(new Member(0, "John", "Doe", "Premium"));
        List<Member> all = service.getAllMembers();
        assertEquals(2, all.size());
    }

    @Test
    void getMemberById() {
        repository.save(new Member(0, "Anna", "Smith", "Standard"));
        int id = repository.members.keySet().iterator().next();
        Member found = service.getMemberById(id);
        assertEquals("Anna", found.getFirstName());
        assertThrows(MemberException.MemberNotFoundException.class, () -> service.getMemberById(999));
    }

    @Test
    void addMember() {
        service.addMember("Mike", "Lee", "Premium");
        assertEquals(1, repository.members.size());
        Member m = repository.members.values().iterator().next();
        assertEquals("Mike", m.getFirstName());
        assertThrows(MemberException.InvalidMemberDataException.class, () -> service.addMember("", "B", "Standard"));
        assertThrows(MemberException.InvalidMemberDataException.class, () -> service.addMember("A", "", "Standard"));
        assertThrows(MemberException.InvalidMemberDataException.class, () -> service.addMember("A", "B", ""));
        assertThrows(MemberException.InvalidMemberDataException.class, () -> service.addMember("A", "B", "Gold"));
        // Duplicate
        service.addMember("John", "Smith", "Standard");
        assertThrows(MemberException.DuplicateMemberException.class, () -> service.addMember("John", "Smith", "Standard"));
    }

    @Test
    void updateMember() {
        repository.save(new Member(0, "Anna", "Smith", "Standard"));
        int id = repository.members.keySet().iterator().next();
        service.updateMember(id, "John", "Doe", "Premium");
        Member updated = repository.findById(id);
        assertEquals("John", updated.getFirstName());
        assertEquals("Premium", updated.getMembershipType());
        assertThrows(MemberException.MemberNotFoundException.class, () ->
                service.updateMember(999, "A", "B", "Standard"));
    }

    @Test
    void removeMember() {
        repository.save(new Member(0, "Anna", "Smith", "Standard"));
        int id = repository.members.keySet().iterator().next();
        service.removeMember(id);
        assertNull(repository.findById(id));
        assertThrows(MemberException.MemberNotFoundException.class, () -> service.removeMember(999));
    }
}