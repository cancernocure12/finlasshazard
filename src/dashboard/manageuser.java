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
        String pass = main.inp.nextLine(); // Password input
        System.out.print("Choose Role (1. Admin, 2. User): ");
        int chooseRole = main.inp.nextInt();
        main.inp.nextLine(); // Consume newline

        String role = "";
        if(chooseRole == 1) {
            role = "Admin";
        } else{
            role = "User";
        }

        config con = new config();
        String hashedPassword = con.hashPassword(pass); // HASH THE PASSWORD
        if(hashedPassword == null) {
            System.out.println("Failed to hash password. User not added.");
            return;
        }

        String sql = "INSERT INTO tbl_user (u_name, u_email, u_pass, u_role, u_status) VALUES(?, ?, ?, ?, ?)";
        con.addRecord(sql, name, email, hashedPassword, role, "Pending");
    }

    public void viewUser(){
        // ... (rest of the method is fine)
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

        System.out.print("Enter new user Password (or press Enter to keep old): ");
        String pass = main.inp.nextLine();

        config con = new config();

        String sqlUpdate;
        String hashedPassword = null;
        if (!pass.trim().isEmpty()) {
            hashedPassword = con.hashPassword(pass); // HASH THE NEW PASSWORD
            sqlUpdate = "UPDATE tbl_user SET u_name = ?, u_email = ?, u_pass = ? WHERE u_id = ? ";
            con.updateRecord(sqlUpdate, name, email, hashedPassword, uid);
        } else {
            // If password is empty, update only name and email
            sqlUpdate = "UPDATE tbl_user SET u_name = ?, u_email = ? WHERE u_id = ? ";
            con.updateRecord(sqlUpdate, name, email, uid);
        }
    }

    public void deleteUser(){
        // ... (rest of the method is fine)
        System.out.print("Enter id to Delete ");
        int did = main.inp.nextInt();
        main.inp.nextLine(); // Consume newline

        config con = new config();
        String sqlDelete = "DELETE FROM tbl_user WHERE u_id = ?";
        con.deleteRecord(sqlDelete, did);
    }

    public void approveUser(){
        // ... (rest of the method is fine)
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
        // String fullname = (String) result.get(0).get("u_name"); // Not used
        // String email = (String) result.get(0).get("u_email"); // Not used
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
     // ... (rest of the method is fine, except for case 5)
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
                     break; // Added break to stop fall-through
                 case 6:
                     main.adminDashboard(uid);
                     return; // Added return to exit the method

                default: System.out.println("\nInvalid input, Try Again.");

            }
            System.out.print("\nDo you want to continue (yes / no): ");
            response = main.inp.next();
        }while(response.equals("yes") || response.equals("1"));
            main.adminDashboard(uid);


        }

 }