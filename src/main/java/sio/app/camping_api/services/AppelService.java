package sio.app.camping_api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
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
                            // Block account and update role
                            try (PreparedStatement blockPstmt = conn.prepareStatement("UPDATE compte SET bloque = true WHERE id_compte = ?")) {
                                blockPstmt.setLong(1, compteId);
                                blockPstmt.executeUpdate();
                            }
                            try (PreparedStatement updateRolePstmt = conn.prepareStatement("UPDATE compte SET role = 'client_bloque' WHERE id_compte = ?")) {
                                updateRolePstmt.setLong(1, compteId);
                                updateRolePstmt.executeUpdate();
                            }

                            // Handle waiting list and update creneaux
                            handleWaitingListAndUpdateCreneaux(compteId);
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

    public void handleWaitingListAndUpdateCreneaux(Long compteId) {
        String getCreneauxQuery = "SELECT id_creneaux FROM inscription WHERE id_compte = ?";
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
             PreparedStatement getCreneauxPstmt = conn.prepareStatement(getCreneauxQuery)) {
            getCreneauxPstmt.setLong(1, compteId);
            try (ResultSet rs = getCreneauxPstmt.executeQuery()) {
                if (rs.next()) {
                    int idCreneaux = rs.getInt("id_creneaux");

                    String checkWaitingListQuery = "SELECT COUNT(*) FROM inscription WHERE id_creneaux = ? AND liste_attente = true";
                    try (PreparedStatement checkWaitingListPstmt = conn.prepareStatement(checkWaitingListQuery)) {
                        checkWaitingListPstmt.setInt(1, idCreneaux);
                        try (ResultSet waitingListRs = checkWaitingListPstmt.executeQuery()) {
                            if (waitingListRs.next() && waitingListRs.getInt(1) > 0) {
                                // Move the first person from the waiting list to the main list
                                String updateListeAttenteQuery = "UPDATE inscription SET liste_attente = false, est_valide = true WHERE id_creneaux = ? AND liste_attente = true ORDER BY date_inscription ASC LIMIT 1";
                                try (PreparedStatement updateListeAttentePstmt = conn.prepareStatement(updateListeAttenteQuery)) {
                                    updateListeAttentePstmt.setInt(1, idCreneaux);
                                    updateListeAttentePstmt.executeUpdate();
                                    LOGGER.log(Level.INFO, "Updated inscription liste_attente");
                                }
                            } else {
                                // Update the creneaux with the new placesPrises
                                String updateQuery = "UPDATE creneaux SET places_prises = places_prises - 1 WHERE id_creneaux = ?";
                                try (PreparedStatement updatePstmt = conn.prepareStatement(updateQuery)) {
                                    updatePstmt.setInt(1, idCreneaux);
                                    updatePstmt.executeUpdate();
                                    LOGGER.log(Level.INFO, "Updated creneaux places_prises");
                                }
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
            // Unblock account, reset absences, and update role
            try (PreparedStatement pstmt = conn.prepareStatement("UPDATE compte SET bloque = false, absences = 0, role = 'client' WHERE id_compte = ?")) {
                pstmt.setLong(1, compteId);
                pstmt.executeUpdate();
                LOGGER.log(Level.INFO, "Account unblocked, absences reset, and role updated for id_compte: {0}", compteId);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception", e);
        }
    }
}