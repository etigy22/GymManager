package com.gymmanager.ui;

import com.gymmanager.repository.*;
import com.gymmanager.service.*;
import com.gymmanager.util.*;


import java.util.Scanner;

public class GymApp {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();

        // Create repositories
        MemberRepository memberRepository = new MemberRepositoryImpl();
        ClassRepository classRepository = new ClassRepositoryImpl();
        EnrollmentRepository enrollmentRepository = new EnrollmentRepositoryImpl();

        // Create services
        MemberService memberService = new MemberServiceImpl(memberRepository);
        ClassService classService = new ClassServiceImpl(classRepository);
        EnrollmentService enrollmentService = new EnrollmentServiceImpl(
                enrollmentRepository, memberRepository, classRepository);

        // Create UI components
        Scanner scanner = new Scanner(System.in);
        MemberUI memberUI = new MemberUI(scanner, memberService);
        ClassUI classUI = new ClassUI(scanner, classService, enrollmentService);

        // Main menu loop
        boolean running = true;
        System.out.println();
        while (running) {
            System.out.println("--- Gym Manager ---");
            System.out.println("1. Manage Members");
            System.out.println("2. Manage Classes");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        memberUI.showMenu();
                        break;
                    case 2:
                        classUI.showMenu();
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
}