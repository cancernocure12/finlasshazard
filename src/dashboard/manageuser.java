
package dashboard;

 import main.main;
 import config.config;
import static main.main.inp;


public class manageuser {
    
    public void addUser(){
        System.out.println("\n=====================");
        System.out.println("=== Add User ===");
         System.out.println("=====================");
        System.out.print("Add user Name: ");
        String name = main.inp.nextLine();
        System.out.print("Enter Email: ");
        String email = main.inp.nextLine();
        System.out.print("Enter Password : ");
        String pass = main.inp.nextLine();
        System.out.print("Choose Role (1. Admin, 2. User): ");
        int chooseRole = main.inp.nextInt();
        
        String role = "";
        if(chooseRole == 1) {
            role = "Admin";
        } else{
            role = "User";
        }
        
        config con = new config();
        String sql = "INSERT INTO tbl_user (u_name, u_email, u_pass, u_role, u_status) VALUES(?, ?, ?, ?, ?)";
        con.addRecord(sql, name, email, pass, role, "Pending");
    }    
    
    public void viewUser(){
    
        config con = new config();
        String hazardQuery = "SELECT * FROM tbl_user";
        String[] hazardHeaders = {"ID", "NAME", "EMAIL", "PASSWORD", "ROLE", "STATUS" };
        String[] hazardColumns = {"u_id", "u_name", "u_email", "u_pass", "u_role", "u_status" };
        
        con.viewRecords(hazardQuery, hazardHeaders, hazardColumns);
    
    }
    
    public void updateUser(){
    
        System.out.print("Enter id to update: ");
        int uid = main.inp.nextInt();
        main.inp.nextLine();
        
        System.out.print("Add new User Name: ");
        String name = main.inp.nextLine();
        System.out.print("Enter new user Email: ");
        String email = main.inp.nextLine();
        
        System.out.print("Enter new user Password: ");
        String pass = main.inp.nextLine();
    
        config con = new config();
        String sqlUpdate = "UPDATE tbl_user SET u_name = ?, u_email = ?, u_pass = ? WHERE u_id = ? ";
        con.updateRecord(sqlUpdate, name, email, pass, uid);
    
    }
    
    public void deleteUser(){
    
        System.out.print("Enter id to Delete ");
        int did = main.inp.nextInt();
        
        config con = new config();
        String sqlDelete = "DELETE FROM tbl_user WHERE u_id = ?";
        con.deleteRecord(sqlDelete, did);
    
    
    
    }
    
    public void approveUser(){
        
        
        config con = new config();
        System.out.print("Enter ID to Approve User: ");
        int aid = main.inp.nextInt();
        main.inp.nextLine();
        
        String fetchQuery = "SELECT u_name, u_email, u_status FROM tbl_user WHERE u_id = ?";
        java.util.List<java.util.Map<String, Object>> result = con.fetchRecords(fetchQuery, aid);

        if (result.isEmpty()) {
            System.out.println("User not found!");
            return;
        }

        String fullname = (String) result.get(0).get("u_name");
        String email = (String) result.get(0).get("u_email");
        String currentStatus = (String) result.get(0).get("u_status");

        if ("Approved".equalsIgnoreCase(currentStatus)) {
            System.out.println("User is already approved.");
            return;
        }

        String sqlUpdate = "UPDATE tbl_user SET u_status = ? WHERE u_id = ?";
        con.updateRecord(sqlUpdate, "Approved", aid);

        System.out.println("\nUser approved successfully!");
        
        
        
        
    
    
    }
    
 public void manageuser (int uid){
     
        String response;
        do{
            System.out.println("\n\n====================================");
            System.out.println("=========| MANAGE USER |========");
            System.out.println("====================================");
          
            System.out.println("1. Approve User  ");
            System.out.println("2. Add User      ");
            System.out.println("3. View User     ");
            System.out.println("4. Update User   ");
            System.out.println("5. Delete User   ");
            System.out.println("6. Exit          ");
            System.out.print("\nChoose an Option: ");
            int option = inp.nextInt();
            inp.nextLine();
        manageuser cd = new manageuser();
            switch(option){
                
                case 1:
                    cd.viewUser();
                    cd.approveUser();
                    break;
                case 2:
                    cd.addUser();
                    break;  
                    
                 case 3:
                    cd.viewUser();
                   
                    break;    
                    
                 case 4:
                    cd.viewUser();
                    cd.updateUser();
                    break;    
                 case 5:
                     cd.viewUser();
                     cd.deleteUser();
                 case 6:
                     main.adminDashboard(uid);
                       
                 break;   
                default: System.out.println("\nInvalid input, Try Again.");
                
            }
            System.out.print("\nDo you want to continue (yes / no): ");
            response = main.inp.next();
        }while(response.equals("yes") || response.equals("1"));
            main.adminDashboard(uid);
            
            
        }

   
 }   
    
    

