package dashboard;

import config.config;
import main.main;

public class managerecords {

    public void manageRecords(int uid) {

        while (true) {
            System.out.println("\n================ MANAGE RECORDS ===============");
            System.out.println("1. View Hazard Records");
            System.out.println("2. Approve Hazard");
            System.out.println("3. Back");

            System.out.print("Choose: ");
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
                { return; }
                 
                 
                default: System.out.println("Invalid.");
            }
        }
    }

    public void viewRecords() {

        config con = new config();

        con.viewRecords(
                "SELECT h_id, h_type, h_location, h_status, user_id FROM tbl_hazard",
                new String[]{"ID", "TYPE", "LOCATION", "STATUS", "USER"},
                new String[]{"h_id", "h_type", "h_location", "h_status", "user_id"}
        );
    }

    public void approveRecord() {

        config con = new config();

        System.out.print("Enter Hazard ID to approve: ");
        int id = main.inp.nextInt();
        main.inp.nextLine();

        con.updateRecord("UPDATE tbl_hazard SET h_status='Approved' WHERE h_id=?", id);

        System.out.println("Hazard approved.");
    }
}
