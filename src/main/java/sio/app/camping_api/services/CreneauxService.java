package sio.app.camping_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CreneauxService {
    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;


    public List<Map<String, Object>> getAllCreneaux() {
        List<Map<String, Object>> creneauxList = new ArrayList<>();
        String query = "SELECT * FROM relation1 "
                + "INNER JOIN compte ON compte.id_compte = relation1.id_compte "
                + "INNER JOIN creneaux ON creneaux.id_creneaux = relation1.id_creneaux "
                + "INNER JOIN animation ON animation.id = creneaux.id "
                + " GROUP BY id_global";
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword); PreparedStatement pstmt = conn.prepareStatement(query); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> creneaux = new HashMap<>();
                creneaux.put("id_creneaux", rs.getInt("id_creneaux"));
                creneaux.put("date_heure", rs.getString("date_heure"));
                creneaux.put("id", rs.getInt("id"));
                creneaux.put("id_lieu", rs.getInt("id_lieu"));
                creneaux.put("Duree", rs.getInt("Duree"));
                creneaux.put("places_totales", rs.getInt("places_totales"));
                creneaux.put("places_prises", rs.getInt("places_prises"));
                creneaux.put("nom_animation", rs.getString("animation.nom"));
                creneaux.put("descriptif_animation", rs.getString("descriptif"));
                creneaux.put("nom_animateur", rs.getString("compte.nom"));
                creneaux.put("prenom_animateur", rs.getString("compte.prenom"));
                creneaux.put("id_global", rs.getInt("id_global"));


                creneauxList.add(creneaux);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return creneauxList;
    }

}
