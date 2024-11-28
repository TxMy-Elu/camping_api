package sio.app.camping_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class InscriptionService {

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    public void updateCreneaux(int nb, int idGlobal) {
        String query = "UPDATE creneaux SET places_prises = places_prises + ? WHERE id_global = ?";
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, nb);
            pstmt.setInt(2, idGlobal);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertInscription(int idCompte, int idCreneaux, Date dateInscription) {
        String query = "INSERT INTO inscription (id_compte, id_creneaux, date_inscription) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idCompte);
            pstmt.setInt(2, idCreneaux);
            pstmt.setDate(3, dateInscription);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}