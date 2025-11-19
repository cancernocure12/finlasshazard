package dashboard;

import main.main;
import config.config;

public class managerecords {
    
    config con = new config();
    
    
     public void approveRecord() {

        System.out.println("\n--- Approve a Hazard Report ---");

        System.out.print("Enter Hazard ID to approve: ");
        int hid = main.inp.nextInt();
        main.inp.nextLine();
       
        String updateQuery = "UPDATE tbl_hazard SET h_status = 'Approved' WHERE h_id = ?";
        con.updateRecord(updateQuery, hid);

        System.out.println("\n✔ Hazard successfully approved!");
    }



    
   public void viewRecords() {

        System.out.println("\n--- Submitted Hazard Reports ---");

        String query =
            "SELECT h_id, h_type, h_location, h_status FROM tbl_hazard";

        String[] headers = {"Hazard ID", "Type", "Location", "Status"};
        String[] cols    = {"h_id", "h_type", "h_location", "h_status"};

        con.viewRecords(query, headers, cols);
    }




    
    public void managerecords(int uid) {

        String response;

        do {
            System.out.println("\n\n====================================");
            System.out.println("=========| MANAGE RECORDS |========");
            System.out.println("====================================");
            System.out.println("1. View Hazard Records");
            System.out.println("2. Approve Hazard");
            System.out.println("3. Exit");

            System.out.print("\nChoose an option: ");
            int option = main.inp.nextInt();
            main.inp.nextLine();

            switch (option) {

                case 1:
                    viewRecords();
                    break;

                case 2:
                    viewRecords();     
                    approveRecord();   
                    break;

                case 3:
                    main.adminDashboard(uid);
                    return;

                default:
                    System.out.print("\nInvalid input, Try Again.");
            }

            System.out.print("\nDo you want to continue (yes / no): ");
            response = main.inp.next();

        } while (response.equalsIgnoreCase("yes") || response.equals("1"));

        main.adminDashboard(uid);
    }
}