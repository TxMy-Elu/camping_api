package sio.app.camping_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    }
