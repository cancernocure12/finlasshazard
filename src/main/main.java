package main;

import config.config;
import dashboard.manageuser;
import java.util.Scanner;
import validation.validation;
import dashboard.managehazard;
import dashboard.managerecords;





public class main {


    public static Scanner inp = new Scanner(System.in);

    public static void main(String[] args) {
        config con = new config();

        validation vl = new validation();
        
        
        System.out.println("========================================================");
        System.out.println("=======| WELCOME TO HAZARD REPORTING SYSTEM | =========");
        System.out.println("=========================================================");
        System.out.println("\n=====| MAIN MENU |=====");
        System.out.println("1. Login   ");
        System.out.println("2. Register ");
        System.out.println("3. Exit     ");

        System.out.print("Choose an Option: ");
        int option = inp.nextInt();
        inp.nextLine();

        switch (option) {

            case 1:
                vl.login();
                break;

            case 2:
                vl.register();
                break;
            case 3:
                System.out.println("Thank you!!");
                break;
            default: System.out.println("Invalid option, Try again..");
            main(null);
        }

    }

    public static void adminDashboard(int uid) {

            config con = new config();
            
            
            System.out.println("\n\n====================================");
            System.out.println("=========| ADMIN DASHBOARD |========");
            System.out.println("====================================");
          
            System.out.println("1. Manage User    ");
            System.out.println("2. Manage Hazard  ");
            System.out.println("3. Manage Records ");
            System.out.println("4. Exit ");

            System.out.print("\nChoose an Option: ");
            int option = inp.nextInt();
            inp.nextLine();

            manageuser cd = new manageuser();

            switch (option) {

                case 1:
                    manageuser mu = new manageuser();
                    mu.manageuser(uid);
                    break;

                case 2:
                    managehazard mh = new managehazard();
                    mh.managehazard(uid);
                    break;

                case 3:
                    managerecords mg = new managerecords();
                    mg.managerecords(uid);
                    break;

                case 4:
                    main(null);
                    break;
                default: System.out.print("Invalid option, Try again..");
                main(null);
            }

    }

    public static void userDashboard(){
    
        String response;
        do {
            
           System.out.println("\n====================================");
           System.out.println("=========| USER DASHBOARD |========");
           System.out.println("====================================");
        
           
            System.out.println("1. Add Hazard    ");
            System.out.println("2. View Hazard   ");
            System.out.println("3. Update Hazard  ");
            System.out.println("4. Delete Hazard ");
            System.out.println("5. Exit  ");
            
        
            System.out.print("\nChoose an option: ");
            int option = inp.nextInt();
            inp.nextLine();
            
            managehazard mh = new managehazard();
            
            switch(option) {
            
                case 1: 
                    mh.addHazard();
                    break;
                    
                case 2: 
                    mh.viewHazard();
                    break;
            
                case 3: 
                    mh.viewHazard();
                    mh.updateHazard();
                    break;
            
                case 4: 
                    mh.viewHazard();
                    mh.deleteHazard();
                    break;
                
                case 5:    
                    main(null);
                    break;
            default: System.out.println("\nInvalid input, Try again.");
            
            
            }
            
            System.out.print("\nDo you want to continue (yes / no): ");
            response = inp.next();
        }while(response.equals("yes") || response.equals("1"));
        main(null);
        return;
            
    }
    
}
        