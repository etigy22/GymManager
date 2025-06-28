package com.gymmanager.repository;

import com.gymmanager.model.Member;

import java.util.List;
/**
 * Interface for managing members in the gym repository.
 * Provides methods to perform CRUD operations on Member entities.
 */
public interface MemberRepository {
    List<Member> findAll();

    Member findById(int memberId);

    boolean existsByName(String firstName, String lastName);

    void save(Member member);

    void update(int memberId, String firstName, String lastName, String membershipType);

    void delete(int memberId);
}
