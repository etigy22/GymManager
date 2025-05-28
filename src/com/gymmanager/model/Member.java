package com.gymmanager.model;

public class Member extends Person {
    private final int memberId;
    private final String membershipType;

    public Member(int memberId, String firstName, String lastName, String membershipType) {
        super(firstName, lastName);
        this.memberId = memberId;
        this.membershipType = membershipType;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    @Override
    public String toString() {
        return "Member ID: " + memberId + ", Name: " + firstName + " " + lastName +
               ", Membership Type: " + membershipType;
    }
}

