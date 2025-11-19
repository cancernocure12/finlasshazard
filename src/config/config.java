package config;

import java.sql.*;
import java.util.*;

public class config {

    public Connection connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:hazard.db");
        } catch (Exception e) {
            System.out.println("DB Error: " + e.getMessage());
            return null;
        }
    }

    // Add
    public void addRecord(String sql, Object... values) {
        try (Connection con = connectDB(); PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++)
                ps.setObject(i + 1, values[i]);

            ps.executeUpdate();
            System.out.println("Record added.");

        } catch (Exception e) {
            System.out.println("Insert error: " + e.getMessage());
        }
    }

    // View
    public void viewRecords(String sql, String[] headers, String[] cols, Object... params) {

        try (Connection con = connectDB(); PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++)
                ps.setObject(i + 1, params[i]);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n--------------------------------------------------------------");
            for (String h : headers) System.out.printf("%-15s", h);
            System.out.println("\n--------------------------------------------------------------");

            while (rs.next()) {
                for (String c : cols) {
                    System.out.printf("%-15s", rs.getString(c));
                }
                System.out.println();
            }

            System.out.println("--------------------------------------------------------------");

        } catch (Exception e) {
            System.out.println("View error: " + e.getMessage());
        }
    }

    // Update
    public void updateRecord(String sql, Object... values) {
        try (Connection con = connectDB(); PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++)
                ps.setObject(i + 1, values[i]);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Update error: " + e.getMessage());
        }
    }

    // Delete
    public void deleteRecord(String sql, Object... values) {
        try (Connection con = connectDB(); PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++)
                ps.setObject(i + 1, values[i]);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Delete error: " + e.getMessage());
        }
    }

    // Fetch
    public List<Map<String, Object>> fetchRecords(String sql, Object... params) {

        List<Map<String, Object>> list = new ArrayList<>();

        try (Connection con = connectDB(); PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++)
                ps.setObject(i + 1, params[i]);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= md.getColumnCount(); i++)
                    row.put(md.getColumnName(i), rs.getObject(i));

                list.add(row);
            }

        } catch (Exception e) {
            System.out.println("Fetch error: " + e.getMessage());
        }

        return list;
    }

    // Login
    public Map<String, Object> login(String email, String pass) {

        String sql = "SELECT u_id, u_role, u_status, u_pass FROM tbl_user WHERE u_email=?";

        try (Connection con = connectDB(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Email not found.");
                return null;
            }

            String storedHash = rs.getString("u_pass");

            if (!storedHash.equals(hashPassword(pass))) {
                System.out.println("Incorrect password.");
                return null;
            }

            if (!"Approved".equalsIgnoreCase(rs.getString("u_status"))) {
                System.out.println("Account pending approval.");
                return null;
            }

            Map<String, Object> info = new HashMap<>();
            info.put("uid", rs.getInt("u_id"));
            info.put("role", rs.getString("u_role"));
            return info;

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            return null;
        }
    }

    // Hashing
    public String hashPassword(String p) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(p.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));

            return sb.toString();

        } catch (Exception e) {
            return null;
        }
    }
}
