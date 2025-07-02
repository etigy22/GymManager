package com.gymmanager.ui;

import com.gymmanager.repository.*;
import com.gymmanager.service.*;
import com.gymmanager.util.*;


import java.util.Scanner;
/** * GymApp is the main entry point for the Gym Manager application.
 * It initializes the database, creates repositories and services,
 * and provides the main user interface for managing members and courses.
 */
public class GymApp {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();

        // Create repositories
        MemberRepository memberRepository = new MemberRepositoryImpl();
        CourseRepository courseRepository = new CourseRepositoryImpl();
        EnrollmentRepository enrollmentRepository = new EnrollmentRepositoryImpl();

        // Create services
        MemberService memberService = new MemberServiceImpl(memberRepository);
        CourseService courseService = new CourseServiceImpl(courseRepository);
        EnrollmentService enrollmentService = new EnrollmentServiceImpl(
                enrollmentRepository, memberRepository, courseRepository);

        // Create UI components
        Scanner scanner = new Scanner(System.in);
        MemberUI memberUI = new MemberUI(scanner, memberService);
        CourseUI courseUI = new CourseUI(scanner, courseService, enrollmentService);

        // Main menu loop
        boolean running = true;
        System.out.println();
        while (running) {
            printMenuOptions();

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        memberUI.showMenu();
                        break;
                    case 2:
                        courseUI.showMenu();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                        System.out.println("Press Enter to continue...");
                        scanner.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                System.out.println("Press Enter to continue...");
                scanner.nextLine();
            }
        }

        DatabaseUtil.closeAllConnections();

        scanner.close();
        System.out.println("Goodbye!");
    }

    private static void printMenuOptions() {
        String[][] menuOptions = {
                {"1", "Manage Members"},
                {"2", "Manage Courses"},
                {"0", "Exit"}
        };
        System.out.println("--- Gym Manager ---");
        for (String[] option : menuOptions) {
            System.out.printf("%s. %s%n", option[0], option[1]);
        }
        System.out.print("Select an option: ");
    }
}