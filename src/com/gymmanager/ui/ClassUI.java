package com.gymmanager.ui;

import com.gymmanager.exception.ServiceException;
import com.gymmanager.model.GymClass;
import com.gymmanager.model.Member;
import com.gymmanager.service.ClassService;
import com.gymmanager.service.EnrollmentService;

import java.util.List;
import java.util.Scanner;

public class ClassUI {
    private final Scanner scanner;
    private final ClassService classService;
    private final EnrollmentService enrollmentService;

    public ClassUI(Scanner scanner, ClassService classService, EnrollmentService enrollmentService) {
        this.scanner = scanner;
        this.classService = classService;
        this.enrollmentService = enrollmentService;
    }

    public void showMenu() {
        boolean back = false;
        System.out.println();
        while (!back) {
            System.out.println("--- Manage Classes ---");
            System.out.println("1. Add Class");
            System.out.println("2. Update Class");
            System.out.println("3. Remove Class");
            System.out.println("4. List Classes");
            System.out.println("5. Enroll Member in Class");
            System.out.println("6. Remove Member from Class");
            System.out.println("7. List Class Members");
            System.out.println("0. Back");
            System.out.print("Select an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                System.out.println();

                switch (choice) {
                    case 1:
                        addClass();
                        break;
                    case 2:
                        updateClass();
                        break;
                    case 3:
                        removeClass();
                        break;
                    case 4:
                        listClasses();
                        break;
                    case 5:
                        enrollMemberInClass();
                        break;
                    case 6:
                        removeMemberFromClass();
                        break;
                    case 7:
                        listClassMembers();
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
                waitForEnter();
            }
        }
    }

    private void addClass() {
        try {
            System.out.print("Enter class name: ");
            String name = scanner.nextLine();

            System.out.print("Enter instructor first name: ");
            String instructorFirstName = scanner.nextLine();

            System.out.print("Enter instructor last name: ");
            String instructorLastName = scanner.nextLine();

            System.out.print("Enter maximum capacity: ");
            int maxCapacity = scanner.nextInt();
            scanner.nextLine();

            classService.addClass(name, instructorFirstName, instructorLastName, maxCapacity);
            System.out.println("Class added successfully.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void updateClass() {
        try {
            List<GymClass> classes = classService.getAllClasses();
            if (classes.isEmpty()) {
                System.out.println("No classes available to update.");
                waitForEnter();
                return;
            }

            displayClasses(classes);

            System.out.print("\nEnter class ID to update: ");
            int classId = scanner.nextInt();
            scanner.nextLine();

            GymClass gymClass = classService.getClassById(classId);

            System.out.print("Enter new name (or press Enter to keep '" +
                    gymClass.getName() + "'): ");
            String name = scanner.nextLine();
            if (name.isEmpty()) {
                name = gymClass.getName();
            }

            System.out.print("Enter new instructor first name (or press Enter to keep '" +
                    gymClass.getInstructorFirstName() + "'): ");
            String instructorFirstName = scanner.nextLine();
            if (instructorFirstName.isEmpty()) {
                instructorFirstName = gymClass.getInstructorFirstName();
            }

            System.out.print("Enter new instructor last name (or press Enter to keep '" +
                    gymClass.getInstructorLastName() + "'): ");
            String instructorLastName = scanner.nextLine();
            if (instructorLastName.isEmpty()) {
                instructorLastName = gymClass.getInstructorLastName();
            }

            System.out.print("Enter new maximum capacity (or -1 to keep " +
                    gymClass.getMaxCapacity() + "): ");
            int maxCapacity = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (maxCapacity == -1) {
                maxCapacity = gymClass.getMaxCapacity();
            }

            classService.updateClass(classId, name, instructorFirstName, instructorLastName, maxCapacity);
            System.out.println("Class updated successfully.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void removeClass() {
        try {
            List<GymClass> classes = classService.getAllClasses();
            if (classes.isEmpty()) {
                System.out.println("No classes available to remove.");
                waitForEnter();
                return;
            }

            displayClasses(classes);

            System.out.print("\nEnter class ID to remove: ");
            int classId = scanner.nextInt();
            scanner.nextLine();

            classService.removeClass(classId);
            System.out.println("Class removed successfully.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void listClasses() {
        try {
            List<GymClass> classes = classService.getAllClasses();
            if (classes.isEmpty()) {
                System.out.println("No classes available.");
            } else {
                displayClasses(classes);
            }
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void enrollMemberInClass() {
        try {
            List<Member> members = enrollmentService.getAllMembers();
            if (members.isEmpty()) {
                System.out.println("No members available for enrollment.");
                waitForEnter();
                return;
            }

            List<GymClass> classes = classService.getAllClasses();
            if (classes.isEmpty()) {
                System.out.println("No classes available for enrollment.");
                waitForEnter();
                return;
            }

            // Display members
            System.out.println("Current Members:");
            for (Member member : members) {
                System.out.println(member);
            }

            // Display classes
            System.out.println();
            displayClasses(classes);

            // Get member ID and class ID
            System.out.print("\nEnter member ID: ");
            int memberId = scanner.nextInt();
            System.out.print("Enter class ID: ");
            int classId = scanner.nextInt();
            scanner.nextLine();

            // Enroll member in class
            enrollmentService.enrollMemberInClass(memberId, classId);
            System.out.println("Successfully enrolled member in class.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void removeMemberFromClass() {
        try {
            // Get all classes
            List<GymClass> classes = classService.getAllClasses();
            if (classes.isEmpty()) {
                System.out.println("No classes available.");
                waitForEnter();
                return;
            }

            // Display classes
            displayClasses(classes);

            // Get the class ID
            System.out.print("\nEnter class ID: ");
            int classId = scanner.nextInt();
            scanner.nextLine();

            // Get members enrolled in this class
            List<Member> enrolledMembers = enrollmentService.getClassMembers(classId);
            if (enrolledMembers.isEmpty()) {
                System.out.println("No members enrolled in this class.");
                waitForEnter();
                return;
            }

            // Show enrolled members
            System.out.println("\nMembers enrolled in this class:");
            for (Member member : enrolledMembers) {
                System.out.println(member);
            }

            // Get member ID to remove
            System.out.print("\nEnter member ID to remove from class: ");
            int memberId = scanner.nextInt();
            scanner.nextLine();

            enrollmentService.removeMemberFromClass(memberId, classId);
            System.out.println("Member removed from class successfully.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void listClassMembers() {
        try {
            List<GymClass> classes = classService.getAllClasses();
            if (classes.isEmpty()) {
                System.out.println("No classes available.");
                waitForEnter();
                return;
            }

            displayClasses(classes);

            System.out.print("\nEnter class ID to list members: ");
            int classId = scanner.nextInt();
            scanner.nextLine();

            List<Member> members = enrollmentService.getClassMembers(classId);
            if (members.isEmpty()) {
                System.out.println("No members enrolled in this class.");
            } else {
                System.out.println("\nMembers enrolled in this class:");
                for (Member member : members) {
                    System.out.println(member);
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void displayClasses(List<GymClass> classes) {
        System.out.println("Current Classes:");
        for (GymClass gymClass : classes) {
            int enrolled = classService.getEnrollmentCount(gymClass.getClassId());
            System.out.println("ID: " + gymClass.getClassId() +
                    ", Name: " + gymClass.getName() +
                    ", Instructor: " + gymClass.getInstructorFirstName() + " " +
                    gymClass.getInstructorLastName() +
                    ", Capacity: " + enrolled + "/" + gymClass.getMaxCapacity());
        }
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