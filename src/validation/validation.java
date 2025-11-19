package validation;
import main.main;
import config.config;
public class validation {

    public void register(){
        config con = new config();

        System.out.print("Add User name: ");
        String name = main.inp.nextLine();
        while(true){
            String qry = "SELECT * FROM tbl_user WHERE u_name = ?";
            java.util.List<java.util.Map<String, Object>> result = con.fetchRecords(qry,name);
            if (result.isEmpty()) {
                break;
            } else {
                System.out.print("Username already exists, Enter other Username: ");
                name = main.inp.nextLine(); // Changed to nextLine to read the whole line
            }
        }

        System.out.print("Enter User Email: ");
        String email = main.inp.nextLine();

        System.out.print("Enter Password: ");
        String pass = main.inp.nextLine();
        System.out.print("Choose role (1. Admin, 2. User): ");
        int chooseRole = main.inp.nextInt();
        main.inp.nextLine(); // Consume newline

        String role = "";
        if(chooseRole == 1){
            role = "Admin";
        }else{
            role = "User";

        }

        String hash = con.hashPassword(pass); // Password hashing

        String sql = "INSERT INTO tbl_user(u_name, u_email, u_pass, u_role, u_status) VALUES(?, ?, ?, ?, ?)";
        con.addRecord(sql, name, email, hash, role, "Pending");
    }
public void login(){
    System.out.print("Enter Email: ");
    String email = main.inp.nextLine();
    System.out.print("Enter Password: ");
    String pass = main.inp.nextLine();

    config con = new config();
    String role = con.login(email, pass);

    if(role != null){
        if (role.equalsIgnoreCase("Admin")){
            // Need to get the UID for adminDashboard.
            // For simplicity, I'll pass 0 as per your original code structure,
            // but ideally, login should return the UID.
            main.adminDashboard(0);
        }else if (role.equalsIgnoreCase("User")){
            main.userDashboard();
        }else {
            System.out.println("Invalid role assigned");

        }
    }
}
}
