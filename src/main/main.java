package main;

import validation.validation;
import dashboard.manageuser;
import dashboard.managehazard;
import dashboard.managerecords;

import java.util.Scanner;

public class main {

    public static Scanner inp = new Scanner(System.in);

    public static void main(String[] args) {
        new main().showMainMenu();
    }

    public void showMainMenu() {

        while (true) {
            System.out.println("\n====================================================");
            System.out.println("========   HAZARD REPORTING SYSTEM   ===============");
            System.out.println("====================================================");

            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");

            System.out.print("Choose an option: ");
            int option = inp.nextInt();
            inp.nextLine();

            validation v = new validation();

            switch (option) {
                case 1:
                    v.login();
                    break;
                case 2:
                    v.register();
                    break;
                case 3: {
                    System.out.println("Thank you for using the system!");
                    System.exit(0);
                }

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    public static void adminDashboard(int uid) {

        manageuser mu = new manageuser();
        managehazard mh = new managehazard();
        managerecords mr = new managerecords();

        while (true) {
            System.out.println("\n=================== ADMIN DASHBOARD ===================");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Hazards");
            System.out.println("3. Manage Records");
            System.out.println("4. Logout");

            System.out.print("Choose an option: ");
            int option = inp.nextInt();
            inp.nextLine();

            switch (option) {
                case 1:
                    mu.manageUser(uid);
                    break;
                case 2:
                    mh.manageHazard(uid);
                    break;
                case 3:
                    mr.manageRecords(uid);
                    break;
                case 4: {
                    System.out.println("Logging out...");
                    return;
                }
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void userDashboard(int uid) {

        managehazard mh = new managehazard();

        while (true) {
            System.out.println("\n=================== USER DASHBOARD ===================");
            System.out.println("1. Add Hazard");
            System.out.println("2. View My Hazards");
            System.out.println("3. Update Hazard");
            System.out.println("4. Delete Hazard");
            System.out.println("5. Logout");

            System.out.print("Choose: ");
            int option = inp.nextInt();
            inp.nextLine();

            switch (option) {
                case 1:
                    mh.viewUserHazards(uid);
                    mh.addHazard(uid);
                    break;
                case 2:
                    mh.viewUserHazards(uid);
                    break;
                case 3:
                    mh.viewUserHazards(uid);
                    mh.updateHazard(uid);
                    break;
                case 4:
                    mh.viewUserHazards(uid);
                    mh.deleteHazard(uid);
                    break;
                case 5: {
                    System.out.println("Logging out...");
                    return;
                }
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

}
