package com.gymmanager.model;
/** Represents a gym course with its details. */
public class Member extends Person {
    private final int memberId;
    private final String membershipType;
    /**
     * Constructs a Member with the specified details.
     *
     * @param memberId the unique identifier for the member
     * @param firstName the first name of the member
     * @param lastName the last name of the member
     * @param membershipType the type of membership (e.g., Standard, Premium)
     */
    public Member(int memberId, String firstName, String lastName, String membershipType) {
        super(firstName, lastName);
        this.memberId = memberId;
        this.membershipType = membershipType;
    }
    /** Gets the unique identifier for the member. */
    public int getMemberId() {
        return memberId;
    }
    /** Gets the type of membership for the member. */
    public String getMembershipType() {
        return membershipType;
    }
    /** Member Information */
    @Override
    public String toString() {
        return "Member ID: " + memberId + ", Name: " + firstName + " " + lastName +
               ", Membership Type: " + membershipType;
    }
}

