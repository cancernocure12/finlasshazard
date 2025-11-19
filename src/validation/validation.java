package validation;

import config.config;
import main.main;
import java.util.List;


import java.util.Map;

public class validation {

    public void register() {

    config con = new config();

    System.out.print("Enter Username: ");
    String name = main.inp.nextLine();

    while (true) {
        List<Map<String, Object>> exists = con.fetchRecords(
            "SELECT u_name FROM tbl_user WHERE u_name = ?", name
        );  // <-- changed to List<Map<String,Object>>

        if (exists.isEmpty()) break;

        System.out.print("Username exists, enter new username: ");
        name = main.inp.nextLine();
    }

    System.out.print("Enter Email: ");
    String email = main.inp.nextLine();

    System.out.print("Enter Password: ");
    String pass = main.inp.nextLine();

    System.out.print("Role (1 = Admin, 2 = User): ");
    int roleChoice = main.inp.nextInt(); 
    main.inp.nextLine();

    String role = (roleChoice == 1) ? "Admin" : "User";
    String status = role.equals("Admin") ? "Approved" : "Pending";

    String sql = "INSERT INTO tbl_user (u_name, u_email, u_pass, u_role, u_status) "
       + "VALUES (?, ?, ?, ?, ?)";

    con.addRecord(sql, name, email, con.hashPassword(pass), role, status);

    System.out.println("\nRegistration successful!");
}

    
    
     public void login() {

    System.out.print("Enter Email: ");
    String email = main.inp.nextLine();

    System.out.print("Enter Password: ");
    String pass = main.inp.nextLine();

    config con = new config();
    Map<String, Object> user = con.login(email, pass);

    if (user == null) {
        System.out.println("Login failed. Please check your email or password, or your account may not be approved yet.");
        return;
    }

    
    Number uidNum = (Number) user.get("uid");
    int uid = uidNum.intValue();

    String role = (String) user.get("role");

    if (role.equalsIgnoreCase("Admin")) {
        main.adminDashboard(uid);
    } else {
     
        main.userDashboard(uid);
    }
    
    
    
     }
    
    
    


    


