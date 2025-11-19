package dashboard;

import config.config;
import main.main;

import java.util.List;
import java.util.Map;

public class managehazard {

    public void manageHazard(int uid) {

        while (true) {
            System.out.println("\n=============== MANAGE HAZARDS =================");
            System.out.println("1. Add Hazard");
            System.out.println("2. View All Hazards");
            System.out.println("3. Update Hazard");
            System.out.println("4. Delete Hazard");
            System.out.println("5. Back");

            System.out.print("Choose: ");
            int option = main.inp.nextInt();
            main.inp.nextLine();

            switch (option) {
                case 1:
                    addHazard(uid);
                    break;
                case 2:
                    viewUserHazards(uid); 
                    break;
                case 3:
                    viewUserHazards(uid); 
                    updateHazard(uid);
                    break;
                case 4:
                    viewUserHazards(uid); 
                    deleteHazard(uid);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void addHazard(int uid) {

        System.out.print("Hazard Type: ");
        String type = main.inp.nextLine();

        System.out.print("Description: ");
        String desc = main.inp.nextLine();

        System.out.print("Location: ");
        String loc = main.inp.nextLine();

        config con = new config();
        con.addRecord(
                "INSERT INTO tbl_hazard (h_type, h_desc, h_location, h_status, user_id) VALUES (?, ?, ?, ?, ?)",
                type, desc, loc, "Pending", uid
        );

        System.out.println("Hazard submitted.");
    }

    public void viewUserHazards(int uid) {
        config con = new config();
        List<Map<String, Object>> records = con.fetchRecords(
                "SELECT * FROM tbl_hazard WHERE user_id=?", uid
        );

        if (records.isEmpty()) {
            System.out.println("No hazards found for this user.");
            return;
        }

        
        System.out.printf("%-5s | %-15s | %-30s | %-20s | %-10s%n",
                "ID", "TYPE", "DESCRIPTION", "LOCATION", "STATUS");
        System.out.println("-------------------------------------------------------------------------------");

        
        for (Map<String, Object> row : records) {
            System.out.printf("%-5s | %-15s | %-30s | %-20s | %-10s%n",
                    row.get("h_id"), row.get("h_type"), row.get("h_desc"),
                    row.get("h_location"), row.get("h_status"));
        }
    }

    public void updateHazard(int uid) {
        config con = new config();

        System.out.print("Enter Hazard ID: ");
        int id = main.inp.nextInt();
        main.inp.nextLine();

        List<Map<String, Object>> check = con.fetchRecords(
                "SELECT * FROM tbl_hazard WHERE h_id=? AND user_id=?", id, uid
        );

        if (check.isEmpty()) {
            System.out.println("No permission or hazard not found.");
            return;
        }

        System.out.print("New Type: ");
        String type = main.inp.nextLine();

        System.out.print("New Description: ");
        String desc = main.inp.nextLine();

        System.out.print("New Location: ");
        String loc = main.inp.nextLine();

        con.updateRecord(
                "UPDATE tbl_hazard SET h_type=?, h_desc=?, h_location=? WHERE h_id=?",
                type, desc, loc, id
        );

        System.out.println("Hazard updated.");
    }

    public void deleteHazard(int uid) {
        config con = new config();

        System.out.print("Enter Hazard ID to delete: ");
        int id = main.inp.nextInt();
        main.inp.nextLine();

        List<Map<String, Object>> check = con.fetchRecords(
                "SELECT * FROM tbl_hazard WHERE h_id=? AND user_id=?", id, uid
        );

        if (check.isEmpty()) {
            System.out.println("You cannot delete this hazard.");
            return;
        }

        con.deleteRecord("DELETE FROM tbl_hazard WHERE h_id=?", id);
        System.out.println("Hazard deleted.");
    }
    
    
    public void viewHazard() {
        config con = new config();
        List<Map<String, Object>> records = con.fetchRecords("SELECT * FROM tbl_hazard");

        if (records.isEmpty()) {
            System.out.println("No hazards found.");
            return;
        }

      
        System.out.printf("%-5s | %-15s | %-30s | %-20s | %-10s | %-5s%n",
                "ID", "TYPE", "DESCRIPTION", "LOCATION", "STATUS", "USER");
        System.out.println("--------------------------------------------------------------------------------------------");

        
        for (Map<String, Object> row : records) {
            System.out.printf("%-5s | %-15s | %-30s | %-20s | %-10s | %-5s%n",
                    row.get("h_id"), row.get("h_type"), row.get("h_desc"),
                    row.get("h_location"), row.get("h_status"), row.get("user_id"));
    
    
    
        }
}

    }











    
   
