package dashboard;

import config.config;
import main.main;

import java.util.List;
import java.util.Map;

public class manageuser {

    public void manageUser(int uid) {

        while (true) {
            System.out.println("\n================ MANAGE USERS ================");
            System.out.println("1. Approve User");
            System.out.println("2. Add User");
            System.out.println("3. View Users");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("6. Back");

            System.out.print("Choose: ");
            int option = main.inp.nextInt();
            main.inp.nextLine();

            switch (option) {
                case 1:
                    viewUser();
                    approveUser();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    viewUser();
                    break;
                case 4:
                    viewUser();
                    updateUser();
                    break;
                case 5:
                    viewUser();
                    deleteUser(uid);
                    break;

                case 6: return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    
    public void addUser() {

        config con = new config();

        System.out.print("Enter Name: ");
        String name = main.inp.nextLine();

        
        while (!con.fetchRecords("SELECT * FROM tbl_user WHERE u_name=?", name).isEmpty()) {
            System.out.print("Username exists. Enter new name: ");
            name = main.inp.nextLine();
        }

        System.out.print("Enter Email: ");
        String email = main.inp.nextLine();

        
        while (!con.fetchRecords("SELECT * FROM tbl_user WHERE u_email=?", email).isEmpty()) {
            System.out.print("Email already registered. Enter new email: ");
            email = main.inp.nextLine();
        }

        System.out.print("Enter Password: ");
        String pass = main.inp.nextLine();

        System.out.print("Role (1=Admin, 2=User): ");
        int roleChoice = main.inp.nextInt();
        main.inp.nextLine();

        String role = (roleChoice == 1) ? "Admin" : "User";
        String status = role.equals("Admin") ? "Approved" : "Pending";

        String sql = "INSERT INTO tbl_user (u_name, u_email, u_pass, u_role, u_status) "
                + "VALUES (?, ?, ?, ?, ?)";

        con.addRecord(sql, name, email, con.hashPassword(pass), role, status);

        System.out.println("\nUser successfully added.");
    }

    
    public void viewUser() {
        config con = new config();
        con.viewRecords(
                "SELECT u_id, u_name, u_email, u_role, u_status FROM tbl_user",
                new String[]{"ID", "Name", "Email", "Role", "Status"},
                new String[]{"u_id", "u_name", "u_email", "u_role", "u_status"}
        );
    }

    
    public void approveUser() {
        config con = new config();

        System.out.print("Enter User ID to approve: ");
        int id = main.inp.nextInt();
        main.inp.nextLine();

        List<Map<String, Object>> check = con.fetchRecords(
                "SELECT * FROM tbl_user WHERE u_id=?", id
        );

        if (check.isEmpty()) {
            System.out.println("User not found.");
            return;
        }

        con.updateRecord("UPDATE tbl_user SET u_status='Approved' WHERE u_id=?", id);

        System.out.println("User approved!");
    }

    
    public void updateUser() {
        config con = new config();

        System.out.print("Enter User ID: ");
        int id = main.inp.nextInt();
        main.inp.nextLine();

        List<Map<String, Object>> check = con.fetchRecords(
                "SELECT * FROM tbl_user WHERE u_id=?", id
        );

        if (check.isEmpty()) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("New Name: ");
        String name = main.inp.nextLine();

        System.out.print("New Email: ");
        String email = main.inp.nextLine();

        System.out.print("New Password (blank = keep old): ");
        String pass = main.inp.nextLine();

        if (pass.isEmpty()) {
            con.updateRecord(
                    "UPDATE tbl_user SET u_name=?, u_email=? WHERE u_id=?",
                    name, email, id
            );
        } else {
            con.updateRecord(
                    "UPDATE tbl_user SET u_name=?, u_email=?, u_pass=? WHERE u_id=?",
                    name, email, con.hashPassword(pass), id
            );
        }

        System.out.println("User updated.");
    }

    
    public void deleteUser(int currentAdminId) {
        config con = new config();

        System.out.print("Enter User ID to delete: ");
        int id = main.inp.nextInt();
        main.inp.nextLine();

        
        if (id == currentAdminId) {
            System.out.println("You cannot delete your own account.");
            return;
        }

       
        if (con.fetchRecords("SELECT * FROM tbl_user WHERE u_id=?", id).isEmpty()) {
            System.out.println("User not found.");
            return;
        }

        con.deleteRecord("DELETE FROM tbl_user WHERE u_id=?", id);
        System.out.println("User deleted.");
    }
}
