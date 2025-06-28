package com.gymmanager.service;

import com.gymmanager.exception.MemberException;
import com.gymmanager.model.Member;
import com.gymmanager.repository.MemberRepository;

import java.util.List;
/**
 * Implementation of the MemberService interface for managing gym members.
 * Provides methods to retrieve, add, update, and remove members.
 */
public class MemberServiceImpl implements MemberService {
    private final MemberRepository repository;
    /** Constructor for MemberServiceImpl.*/
    public MemberServiceImpl(MemberRepository repository) {
        this.repository = repository;
    }
    /** Retrieves all members from the repository. */
    @Override
    public List<Member> getAllMembers() {
        return repository.findAll();
    }
    /** Retrieves a member by their ID. */
    @Override
    public Member getMemberById(int id) {
        Member member = repository.findById(id);
        if (member == null) {
            throw new MemberException.MemberNotFoundException(id);
        }
        return member;
    }
    /** Adds a new member to the repository. */
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
    /** Updates an existing member's information in the repository. */
    @Override
    public void updateMember(int id, String firstName, String lastName, String membershipType) {
        validateMemberData(firstName, lastName, membershipType);
        getMemberById(id);

        repository.update(id, firstName, lastName, membershipType);
    }
    /** Removes a member from the repository by their ID. */
    @Override
    public void removeMember(int id) {
        getMemberById(id);

        repository.delete(id);
    }
    /** Validates the member data before adding or updating a member. */
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