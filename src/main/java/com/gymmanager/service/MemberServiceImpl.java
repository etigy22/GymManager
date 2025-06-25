package com.gymmanager.service;

import com.gymmanager.exception.MemberException;
import com.gymmanager.model.Member;
import com.gymmanager.repository.MemberRepository;

import java.util.List;

public class MemberServiceImpl implements MemberService {
    private final MemberRepository repository;

    public MemberServiceImpl(MemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Member> getAllMembers() {
        return repository.findAll();
    }

    @Override
    public Member getMemberById(int id) {
        Member member = repository.findById(id);
        if (member == null) {
            throw new MemberException.MemberNotFoundException(id);
        }
        return member;
    }

    @Override
    public void addMember(String firstName, String lastName, String membershipType) {
        validateMemberData(firstName, lastName, membershipType);
        if (repository.existsByName(firstName, lastName)) {
            throw new MemberException.DuplicateMemberException(firstName, lastName);
        } else {
            Member member = new Member(0, firstName, lastName, membershipType);
            repository.save(member);
        }
    }

    @Override
    public void updateMember(int id, String firstName, String lastName, String membershipType) {
        validateMemberData(firstName, lastName, membershipType);
        getMemberById(id);

        repository.update(id, firstName, lastName, membershipType);
    }

    @Override
    public void removeMember(int id) {
        getMemberById(id);

        repository.delete(id);
    }

    private void validateMemberData(String firstName, String lastName, String membershipType) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new MemberException.InvalidMemberDataException("First name cannot be empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new MemberException.InvalidMemberDataException("Last name cannot be empty");
        }
        if (membershipType == null || membershipType.trim().isEmpty()) {
            throw new MemberException.InvalidMemberDataException("Membership type cannot be empty");
        }
        if (!membershipType.equalsIgnoreCase("Standard") && !membershipType.equalsIgnoreCase("Premium")) {
            throw new MemberException.InvalidMemberDataException("Membership type must be either 'Standard' or 'Premium'");
        }
    }
}