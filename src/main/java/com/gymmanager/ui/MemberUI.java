package com.gymmanager.ui;

import com.gymmanager.exception.ServiceException;
import com.gymmanager.model.Member;
import com.gymmanager.service.MemberService;

import java.util.List;
import java.util.Scanner;
/**
 * MemberUI class provides a user interface for managing gym members.
 * It allows adding, updating, removing, and listing members.
 */
public class MemberUI {
    private final Scanner scanner;
    private final MemberService memberService;
    /** Constructor for MemberUI. */
    public MemberUI(Scanner scanner, MemberService memberService) {
        this.scanner = scanner;
        this.memberService = memberService;
    }
    /** Displays the member management menu and handles user input. */
    public void showMenu() {
        boolean back = false;
        System.out.println();
        while (!back) {
            System.out.println("--- Manage Members ---");
            System.out.println("1. Add Member");
            System.out.println("2. Update Member");
            System.out.println("3. Remove Member");
            System.out.println("4. List Members");
            System.out.println("0. Back");
            System.out.print("Select an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.println();

                switch (choice) {
                    case 1:
                        addMember();
                        break;
                    case 2:
                        updateMember();
                        break;
                    case 3:
                        removeMember();
                        break;
                    case 4:
                        listMembers();
                        break;
                    case 0:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option.");
                        waitForEnter();
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                waitForEnter();
            }
        }
    }

    private void addMember() {
        try {
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();

            String membershipType;
            while (true) {
                System.out.print("Enter membership type (Standard/Premium): ");
                membershipType = scanner.nextLine();
                if (membershipType.equalsIgnoreCase("Standard") ||
                        membershipType.equalsIgnoreCase("Premium")) {
                    break;
                }
                System.out.println("Invalid membership type. Please enter Standard or Premium.");
            }

            memberService.addMember(firstName, lastName, membershipType);
            System.out.println("Member added successfully.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void updateMember() {
        try {
            List<Member> members = memberService.getAllMembers();
            if (members.isEmpty()) {
                System.out.println("No members available to update.");
                waitForEnter();
                return;
            }

            System.out.println("Current Members:");
            for (Member member : members) {
                System.out.println(member);
            }

            System.out.print("\nEnter member ID to update: ");
            int memberId = scanner.nextInt();
            scanner.nextLine();

            Member member = memberService.getMemberById(memberId);

            System.out.print("Enter new first name (or press Enter to keep '" + member.getFirstName() + "'): ");
            String firstName = scanner.nextLine();
            if (firstName.isEmpty()) {
                firstName = member.getFirstName();
            }

            System.out.print("Enter new last name (or press Enter to keep '" +
                    member.getLastName() + "'): ");
            String lastName = scanner.nextLine();
            if (lastName.isEmpty()) {
                lastName = member.getLastName();
            }

            String membershipType = member.getMembershipType();
            while (true) {
                System.out.print("Enter new membership type (Standard/Premium) or press Enter to keep '" +
                        membershipType + "'): ");
                String newType = scanner.nextLine();
                if (newType.isEmpty()) {
                    break;
                }
                if (newType.equalsIgnoreCase("Standard") ||
                        newType.equalsIgnoreCase("Premium")) {
                    membershipType = newType;
                    break;
                }
                System.out.println("Invalid membership type. Please enter Standard or Premium.");
            }

            memberService.updateMember(memberId, firstName, lastName, membershipType);
            System.out.println("Member updated successfully.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void removeMember() {
        try {
            List<Member> members = memberService.getAllMembers();
            if (members.isEmpty()) {
                System.out.println("No members available to remove.");
                waitForEnter();
                return;
            }

            System.out.println("Current Members:");
            for (Member member : members) {
                System.out.println(member);
            }

            System.out.print("\nEnter member ID to remove: ");
            int memberId = scanner.nextInt();
            scanner.nextLine();

            memberService.removeMember(memberId);
            System.out.println("Member removed successfully.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void listMembers() {
        try {
            List<Member> members = memberService.getAllMembers();
            if (members.isEmpty()) {
                System.out.println("No members available.");
            } else {
                System.out.println("Current Members:");
                for (Member member : members) {
                    System.out.println(member);
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void waitForEnter() {
        System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
    }

    private void handleException(Exception e) {
        String errorMessage = e instanceof ServiceException ?
                "Error: " + e.getMessage() :
                "An unexpected error occurred: " + e.getMessage();
        System.out.println(errorMessage);
    }
}

