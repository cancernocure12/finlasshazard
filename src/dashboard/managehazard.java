package dashboard;

import main.main;
import config.config;
import static main.main.main;


public class managehazard {
    
    public void managehazard(int uid){
    
      String response;
    
        do{
           System.out.println("\n\n====================================");
            System.out.println("=========| MANAGE HAZARD |========");
            System.out.println("====================================");
            System.out.println("1. Add Hazard    ");
            System.out.println("2. View Hazard   ");
            System.out.println("3. Update Hazard ");
            System.out.println("4. Delete Hazard ");
            System.out.println("5. Exit ");

            System.out.print("\nChoose an option: ");
            int option = main.inp.nextInt();
            main.inp.nextLine();
            
            switch(option){
                
                case 1:
                    addHazard();
                    break;
                    
                case 2:
                    viewHazard();
                    break;
                    
                case 3:
                    viewHazard();
                    updateHazard();
                    break;    
                    
                case 4:
                    viewHazard();
                    deleteHazard();
                    break;    
                case 5:    
                    main.adminDashboard(uid);
                    break;
                    
                    default: System.out.print("\nInvalid input, Try Again."); 
            }
    
            System.out.print("\nDo you want to continue (yes / no): ");
            response = main.inp.next();
            }while(response.equals("yes") || response.equals("1"));
            main.adminDashboard(uid);
            
            
    }
        
        public void addHazard(){
        
        
            System.out.println("\n\n=================================");
            System.out.println("=========| ADD HAZARD  |========");
            System.out.println("====================================");
        
            System.out.print("Enter Hazard Type: ");
            String type = main.inp.nextLine();
            System.out.print("Enter Description: ");
            String desc = main.inp.nextLine();
            System.out.print("Enter Location: ");
            String loc = main.inp.nextLine();
            
             config con = new config();
        
        String sql = "INSERT INTO tbl_hazard (h_type, h_desc, h_location, h_status) VALUES (?, ?, ?, ?)";
        con.addRecord(sql, type, desc, loc, "pending");
        
        }
        
    public void viewHazard(){
    
        config con = new config();
        String mtsiQuery = "SELECT * FROM tbl_hazard";
        String[] mtsiHeaders = {"ID", "TYPE", "DESCRIPTION", "LOCATION" };
        String[] mtsiColumns = {"h_id", "h_type", "h_desc", "h_location" };
        con.viewRecords(mtsiQuery, mtsiHeaders, mtsiColumns);
        
    
    }
    
    
    
    public void updateHazard(){
    
          config con = new config();
          
           System.out.print("Enter Product ID to Update: ");
           int uid = main.inp.nextInt();
           main.inp.nextLine();
           
           String checkQry = "SELECT * FROM tbl_hazard WHERE h_id = ?";
        java.util.List<java.util.Map<String, Object>> checkResult = con.fetchRecords(checkQry, uid);

        if (checkResult.isEmpty()) {
            System.out.println("Invalid ID. Product not found.");
            return;
        }
    
        System.out.print("Enter New Hazard Name/Type: ");
        String type = main.inp.nextLine();
        
        System.out.print("Enter New Description: ");
        String desc = main.inp.nextLine();
        
        System.out.print("Enter New Location: ");
        String loc = main.inp.nextLine();

        
        String sqlUpdate = "UPDATE tbl_hazard SET h_type = ?, h_desc = ?, h_location = ? WHERE h_id = ?";
        con.updateRecord(sqlUpdate, type, desc, loc, uid);

        
        System.out.println("\nRecord updated successfully!");
    
    
    }
    
    public void deleteHazard(){
    
    
        config con = new config();
        
        System.out.print("Enter Hazard ID to Delete: ");
        int did = main.inp.nextInt();
        main.inp.nextLine();
        
        String checkQry = "SELECT * FROM tbl_hazard WHERE h_id = ?";
        java.util.List<java.util.Map<String, Object>> checkResult = con.fetchRecords(checkQry, did);

        if (checkResult.isEmpty()) {
            System.out.println("Invalid ID. Product not found.");
            return;
        }
        
        String sqlDelete = "DELETE FROM tbl_hazard WHERE h_id = ?";
        con.deleteRecord(sqlDelete, did);
    
    
    
    }
    
    
}