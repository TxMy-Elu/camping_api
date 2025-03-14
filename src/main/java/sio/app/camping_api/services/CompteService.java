package sio.app.camping_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sio.app.camping_api.secutity.JwtUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompteService {

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Autowired
    private JwtUtils jwtUtils;

    public List<Map<String, Object>> getAllCompte() {
        List<Map<String, Object>> compteList = new ArrayList<>();
        String query = "SELECT * FROM compte ";

        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> compte = new HashMap<>();
                compte.put("id_compte", rs.getInt("id_compte"));
                compte.put("nom", rs.getString("compte.nom"));
                compte.put("prenom", rs.getString("compte.prenom"));
                compte.put("email", rs.getString("email"));
                compte.put("password", rs.getString("password"));
                compte.put("role", rs.getString("role"));
                compte.put("absences", rs.getInt("absences"));
                compteList.add(compte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compteList;
    }

    public List<Map<String, Object>> getCompteBloque() {
        List<Map<String, Object>> compteListBloque = new ArrayList<>();
        String query = "SELECT * FROM compte where bloque = true";

        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> compte = new HashMap<>();
                compte.put("id_compte", rs.getInt("id_compte"));
                compte.put("nom", rs.getString("compte.nom"));
                compte.put("prenom", rs.getString("compte.prenom"));
                compte.put("email", rs.getString("email"));
                compte.put("password", rs.getString("password"));
                compte.put("role", rs.getString("role"));
                compte.put("absences", rs.getInt("absences"));
                compteListBloque.add(compte);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compteListBloque;
    }

    public Map<String, Object> getRole(String jwt) {
        String email = jwtUtils.getEmailFromJwtToken(jwt);
        Long compteId = findCompteIdByEmail(email);
        String role = getRoleByCompteId(compteId);

        Map<String, Object> response = new HashMap<>();
        response.put("id_compte", compteId);
        response.put("email", email);
        response.put("role", role);
        return response;
    }

    public Long findCompteIdByEmail(String email) {
        Long compteId = null;
        String query = "SELECT id_compte FROM compte WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                compteId = rs.getLong("id_compte");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compteId;
    }

    public String getRoleByCompteId(Long compteId) {
        String role = null;
        String query = "SELECT role FROM compte WHERE id_compte = ?";
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, compteId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }
}


