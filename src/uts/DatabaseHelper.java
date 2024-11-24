/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts;

/**
 *
 * @author asus
 */
import java.sql.*;
public class DatabaseHelper {
    private Connection conn;
     // Constructor untuk koneksi dan inisialisasi database
   public DatabaseHelper(String dbFile) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            createTableIfNotExists();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS catatan ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "judul TEXT NOT NULL, "
                + "isi TEXT NOT NULL, "
                + "tanggal TEXT NOT NULL)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public ResultSet getAllNotes() {
        String query = "SELECT * FROM catatan";
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Error fetching notes: " + e.getMessage());
        }
        return null;
    }

    public void addNote(String judul, String isi, String tanggal) {
        String query = "INSERT INTO catatan (judul, isi, tanggal) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, judul);
            pstmt.setString(2, isi);
            pstmt.setString(3, tanggal);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding note: " + e.getMessage());
        }
    }

    public void updateNote(int id, String judul, String isi, String tanggal) {
        String query = "UPDATE catatan SET judul = ?, isi = ?, tanggal = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, judul);
            pstmt.setString(2, isi);
            pstmt.setString(3, tanggal);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating note: " + e.getMessage());
        }
    }

    public void deleteNote(int id) {
        String query = "DELETE FROM catatan WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting note: " + e.getMessage());
        }
    }
}

