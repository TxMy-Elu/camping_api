package sio.app.camping_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class InscriptionService {

        @Value("${spring.datasource.url}")
        private String databaseUrl;
        @Value("${spring.datasource.username}")
        private String databaseUsername;
        @Value("${spring.datasource.password}")
        private String databasePassword;

       public int nb;
        public List<Map<String, Object>> UpdateNbPlace( int id_global) {
            List<Map<String, Object>> inscriptionList = new ArrayList<>();
            String query = "UPDATE creneaux\n"
                    + "SET places_prises = places_prises+nb"
                    + "WHERE id_global = id_global";
            try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> incription = new HashMap<>();
                    incription.put("places_prises", rs.getInt("places_prises"));

                    incription.put("nb", rs.getInt(nb));
                    incription.put("id_global", rs.getInt(id_global));


                    inscriptionList.add(incription);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return inscriptionList;
        }

    }

