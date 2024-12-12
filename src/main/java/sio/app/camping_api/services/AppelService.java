package sio.app.camping_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AppelService {

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    private static final Logger LOGGER = Logger.getLogger(AppelService.class.getName());

   public void gererAbsence(Long compteId, boolean estAbsent) {
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword)) {
            if (estAbsent) {
                // Increment absences
                try (PreparedStatement pstmt = conn.prepareStatement("UPDATE compte SET absences = absences + 1 WHERE id_compte = ?")) {
                    pstmt.setLong(1, compteId);
                    pstmt.executeUpdate();
                }

                // Check if absences >= 3
                try (PreparedStatement pstmt = conn.prepareStatement("SELECT absences FROM compte WHERE id_compte = ?")) {
                    pstmt.setLong(1, compteId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next() && rs.getInt("absences") >= 3) {
                            // Block account
                            try (PreparedStatement blockPstmt = conn.prepareStatement("UPDATE compte SET bloque = true WHERE id_compte = ?")) {
                                blockPstmt.setLong(1, compteId);
                                blockPstmt.executeUpdate();
                            }
                        }
                    }
                }
            } else {
                // Check if absences > 0 before decrementing
                try (PreparedStatement pstmt = conn.prepareStatement("SELECT absences FROM compte WHERE id_compte = ?")) {
                    pstmt.setLong(1, compteId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next() && rs.getInt("absences") > 0) {
                            // Decrement absences
                            try (PreparedStatement updatePstmt = conn.prepareStatement("UPDATE compte SET absences = absences - 1 WHERE id_compte = ?")) {
                                updatePstmt.setLong(1, compteId);
                                updatePstmt.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception", e);
        }
    }

    public void debloquerCompte(Long compteId) {
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword)) {
            // Unblock account and reset absences
            try (PreparedStatement pstmt = conn.prepareStatement("UPDATE compte SET bloque = false, absences = 0 WHERE id_compte = ?")) {
                pstmt.setLong(1, compteId);
                pstmt.executeUpdate();
                LOGGER.log(Level.INFO, "Account unblocked and absences reset for id_compte: {0}", compteId);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception", e);
        }
    }
}