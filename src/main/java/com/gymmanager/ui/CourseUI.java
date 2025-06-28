package com.gymmanager.ui;

import com.gymmanager.exception.ServiceException;
import com.gymmanager.model.GymCourse;
import com.gymmanager.model.Member;
import com.gymmanager.service.CourseService;
import com.gymmanager.service.EnrollmentService;

import java.util.List;
import java.util.Scanner;
/**
 * CourseUI class provides a user interface for managing gym courses.
 * It allows users to add, update, remove, and list courses,
 * as well as enroll and remove members from courses.
 */
public class CourseUI {
    private final Scanner scanner;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    /** Constructor to initialize CourseUI with necessary services. */
    public CourseUI(Scanner scanner, CourseService courseService, EnrollmentService enrollmentService) {
        this.scanner = scanner;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }
    /** Displays the course management menu and handles user input. */
    public void showMenu() {
        boolean back = false;
        System.out.println();
        while (!back) {
            System.out.println("--- Manage Courses ---");
            System.out.println("1. Add Course");
            System.out.println("2. Update Course");
            System.out.println("3. Remove Course");
            System.out.println("4. List Courses");
            System.out.println("5. Enroll Member in Course");
            System.out.println("6. Remove Member from Course");
            System.out.println("7. List Course Members");
            System.out.println("0. Back");
            System.out.print("Select an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                System.out.println();

                switch (choice) {
                    case 1:
                        addCourse();
                        break;
                    case 2:
                        updateCourse();
                        break;
                    case 3:
                        removeCourse();
                        break;
                    case 4:
                        listCourses();
                        break;
                    case 5:
                        enrollMemberInCourse();
                        break;
                    case 6:
                        removeMemberFromCourse();
                        break;
                    case 7:
                        listCourseMembers();
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

    private void addCourse() {
        try {
            System.out.print("Enter course name: ");
            String name = scanner.nextLine();

            System.out.print("Enter instructor first name: ");
            String instructorFirstName = scanner.nextLine();

            System.out.print("Enter instructor last name: ");
            String instructorLastName = scanner.nextLine();

            System.out.print("Enter maximum capacity: ");
            int maxCapacity = scanner.nextInt();
            scanner.nextLine();

            courseService.addCourse(name, instructorFirstName, instructorLastName, maxCapacity);
            System.out.println("Course added successfully.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void updateCourse() {
        try {
            List<GymCourse> courses = courseService.getAllCourses();
            if (courses.isEmpty()) {
                System.out.println("No courses available to update.");
                waitForEnter();
                return;
            }

            displayCourses(courses);

            System.out.print("\nEnter course ID to update: ");
            int courseId = scanner.nextInt();
            scanner.nextLine();

            GymCourse gymCourse = courseService.getCourseById(courseId);

            System.out.print("Enter new name (or press Enter to keep '" +
                    gymCourse.getName() + "'): ");
            String name = scanner.nextLine();
            if (name.isEmpty()) {
                name = gymCourse.getName();
            }

            System.out.print("Enter new instructor first name (or press Enter to keep '" +
                    gymCourse.getInstructorFirstName() + "'): ");
            String instructorFirstName = scanner.nextLine();
            if (instructorFirstName.isEmpty()) {
                instructorFirstName = gymCourse.getInstructorFirstName();
            }

            System.out.print("Enter new instructor last name (or press Enter to keep '" +
                    gymCourse.getInstructorLastName() + "'): ");
            String instructorLastName = scanner.nextLine();
            if (instructorLastName.isEmpty()) {
                instructorLastName = gymCourse.getInstructorLastName();
            }

            System.out.print("Enter new maximum capacity (or -1 to keep " +
                    gymCourse.getMaxCapacity() + "): ");
            int maxCapacity = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (maxCapacity == -1) {
                maxCapacity = gymCourse.getMaxCapacity();
            }

            courseService.updateCourse(courseId, name, instructorFirstName, instructorLastName, maxCapacity);
            System.out.println("Course updated successfully.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void removeCourse() {
        try {
            List<GymCourse> courses = courseService.getAllCourses();
            if (courses.isEmpty()) {
                System.out.println("No courses available to remove.");
                waitForEnter();
                return;
            }

            displayCourses(courses);

            System.out.print("\nEnter course ID to remove: ");
            int courseId = scanner.nextInt();
            scanner.nextLine();

            courseService.removeCourse(courseId);
            System.out.println("Course removed successfully.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void listCourses() {
        try {
            List<GymCourse> courses = courseService.getAllCourses();
            if (courses.isEmpty()) {
                System.out.println("No courses available.");
            } else {
                displayCourses(courses);
            }
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void enrollMemberInCourse() {
        try {
            List<Member> members = enrollmentService.getAllMembers();
            if (members.isEmpty()) {
                System.out.println("No members available for enrollment.");
                waitForEnter();
                return;
            }

            List<GymCourse> courses = courseService.getAllCourses();
            if (courses.isEmpty()) {
                System.out.println("No courses available for enrollment.");
                waitForEnter();
                return;
            }

            System.out.println("Current Members:");
            for (Member member : members) {
                System.out.println(member);
            }

            System.out.println();
            displayCourses(courses);

            System.out.print("\nEnter member ID: ");
            int memberId = scanner.nextInt();
            System.out.print("Enter course ID: ");
            int courseId = scanner.nextInt();
            scanner.nextLine();

            enrollmentService.enrollMemberInCourse(memberId, courseId);
            System.out.println("Successfully enrolled member in course.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void removeMemberFromCourse() {
        try {
            List<GymCourse> courses = courseService.getAllCourses();
            if (courses.isEmpty()) {
                System.out.println("No courses available.");
                waitForEnter();
                return;
            }

            displayCourses(courses);

            System.out.print("\nEnter course ID: ");
            int courseId = scanner.nextInt();
            scanner.nextLine();

            List<Member> enrolledMembers = enrollmentService.getCourseMembers(courseId);
            if (enrolledMembers.isEmpty()) {
                System.out.println("No members enrolled in this course.");
                waitForEnter();
                return;
            }

            System.out.println("\nMembers enrolled in this course:");
            for (Member member : enrolledMembers) {
                System.out.println(member);
            }

            System.out.print("\nEnter member ID to remove from course: ");
            int memberId = scanner.nextInt();
            scanner.nextLine();

            enrollmentService.removeMemberFromCourse(memberId, courseId);
            System.out.println("Member removed from course successfully.");
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void listCourseMembers() {
        try {
            List<GymCourse> courses = courseService.getAllCourses();
            if (courses.isEmpty()) {
                System.out.println("No courses available.");
                waitForEnter();
                return;
            }

            displayCourses(courses);

            System.out.print("\nEnter course ID to list members: ");
            int courseId = scanner.nextInt();
            scanner.nextLine();

            List<Member> members = enrollmentService.getCourseMembers(courseId);
            if (members.isEmpty()) {
                System.out.println("No members enrolled in this course.");
            } else {
                System.out.println("\nMembers enrolled in this course:");
                for (Member member : members) {
                    System.out.println(member);
                }
            }
        } catch (Exception e) {
            handleException(e);
        }
        waitForEnter();
    }

    private void displayCourses(List<GymCourse> courses) {
        System.out.println("Current Courses:");
        for (GymCourse gymCourse : courses) {
            int enrolled = courseService.getEnrollmentCount(gymCourse.getCourseId());
            System.out.println("ID: " + gymCourse.getCourseId() +
                    ", Name: " + gymCourse.getName() +
                    ", Instructor: " + gymCourse.getInstructorFirstName() + " " +
                    gymCourse.getInstructorLastName() +
                    ", Capacity: " + enrolled + "/" + gymCourse.getMaxCapacity());
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