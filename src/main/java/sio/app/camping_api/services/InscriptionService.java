package sio.app.camping_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sio.app.camping_api.secutity.JwtUtils;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class InscriptionService {

    private static final Logger LOGGER = Logger.getLogger(InscriptionService.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("logs/api.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.INFO);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize file handler for logger", e);
        }
    }

    @Value("${spring.datasource.url}")
    private String databaseUrl;
    @Value("${spring.datasource.username}")
    private String databaseUsername;
    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Autowired
    private JwtUtils jwtUtils;

    public void insertInscription(String jwt, int idCreneaux, Date dateInscription) {
        String email = jwtUtils.getEmailFromJwtToken(jwt);
        int idCompte = getIdCompteByEmail(email);
        String query = "SELECT id_global, places_totales, places_prises FROM creneaux WHERE id_creneaux = ?";
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idCreneaux);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id_global = rs.getInt("id_global");
                    int placesTotales = rs.getInt("places_totales");
                    int placesPrises = rs.getInt("places_prises");
                    LOGGER.log(Level.INFO, "id_global: {0}, places_totales: {1}, places_prises: {2}", new Object[]{id_global, placesTotales, placesPrises});
                    // log la requête
                    LOGGER.log(Level.INFO, "Selected creneaux with id_creneaux: {0}", idCreneaux);
                    if (placesTotales == placesPrises) {
                        // Insert inscription with liste_attente set to true
                        String insertQuery = "INSERT INTO inscription (id_compte, id_creneaux, date_inscription, liste_attente) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertPstmt = conn.prepareStatement(insertQuery)) {
                            insertPstmt.setInt(1, idCompte);
                            insertPstmt.setInt(2, idCreneaux);
                            insertPstmt.setDate(3, dateInscription);
                            insertPstmt.setBoolean(4, true);
                            insertPstmt.executeUpdate();
                            LOGGER.log(Level.INFO, "Inserted into inscription with liste_attente = true");
                        }
                    } else {
                        // Insert inscription with liste_attente set to false
                        String insertQuery = "INSERT INTO inscription (id_compte, id_creneaux, date_inscription, liste_attente) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertPstmt = conn.prepareStatement(insertQuery)) {
                            insertPstmt.setInt(1, idCompte);
                            insertPstmt.setInt(2, idCreneaux);
                            insertPstmt.setDate(3, dateInscription);
                            insertPstmt.setBoolean(4, false);
                            insertPstmt.executeUpdate();
                            LOGGER.log(Level.INFO, "Inserted into inscription with liste_attente = false");
                        }
                        // Update the creneaux with the new placesPrises
                        String updateQuery = "UPDATE creneaux SET places_prises = places_prises + 1 WHERE id_global = ?";
                        try (PreparedStatement updatePstmt = conn.prepareStatement(updateQuery)) {
                            updatePstmt.setInt(1, id_global);
                            updatePstmt.executeUpdate();
                            LOGGER.log(Level.INFO, "Updated creneaux places_prises");
                        }
                    }
                } else {
                    LOGGER.log(Level.WARNING, "No creneaux found with id_creneaux: {0}", idCreneaux);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception", e);
        }
    }

    public void deleteInscription(String jwt, int idCreneaux) {
        String email = jwtUtils.getEmailFromJwtToken(jwt);
        int idCompte = getIdCompteByEmail(email);
        String query = "SELECT id_global, places_prises FROM creneaux WHERE id_creneaux = ?";
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idCreneaux);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id_global = rs.getInt("id_global");
                    int placesPrises = rs.getInt("places_prises");
                    LOGGER.log(Level.INFO, "id_global: {0}, places_prises: {1}", new Object[]{id_global, placesPrises});
                    // log la requête
                    LOGGER.log(Level.INFO, "Selected creneaux with id_creneaux: {0}", idCreneaux);
                    // Delete inscription
                    String deleteQuery = "DELETE FROM inscription WHERE id_compte = ? AND id_creneaux = ?";
                    try (PreparedStatement deletePstmt = conn.prepareStatement(deleteQuery)) {
                        deletePstmt.setInt(1, idCompte);
                        deletePstmt.setInt(2, idCreneaux);
                        // log la requête
                        LOGGER.log(Level.INFO, "Deleted from inscription with id_compte: {0}, id_creneaux: {1}", new Object[]{idCompte, idCreneaux});
                        deletePstmt.executeUpdate();
                        LOGGER.log(Level.INFO, "Deleted from inscription");
                    } catch ( SQLException e) {
                        LOGGER.log(Level.SEVERE, "SQL Exception", e);
                    }
                    // Check if there is anyone on the waiting list
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
                                String updateQuery = "UPDATE creneaux SET places_prises = places_prises - 1 WHERE id_global = ?";
                                try (PreparedStatement updatePstmt = conn.prepareStatement(updateQuery)) {
                                    updatePstmt.setInt(1, id_global);
                                    updatePstmt.executeUpdate();
                                    LOGGER.log(Level.INFO, "Updated creneaux places_prises");
                                }
                            }
                        }
                    }
                } else {
                    LOGGER.log(Level.WARNING, "No creneaux found with id_creneaux: {0}", idCreneaux);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception", e);
        }
    }

    public List<Map<String, Object>> getRegisteredUsers(Long activiteId) {
        List<Map<String, Object>> registeredUsers = new ArrayList<>();
        String query = "SELECT c.id_compte, c.nom, c.prenom, c.email, i.estAbs FROM inscription i JOIN compte c ON i.id_compte = c.id_compte WHERE i.id_creneaux = ? AND i.est_valide = true";
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, activiteId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> user = new HashMap<>();
                    user.put("id_compte", rs.getLong("id_compte"));
                    user.put("nom", rs.getString("nom"));
                    user.put("prenom", rs.getString("prenom"));
                    user.put("email", rs.getString("email"));
                    user.put("estAbs", rs.getBoolean("estAbs"));
                    registeredUsers.add(user);
                    LOGGER.log(Level.INFO, "Selected id_compte, nom, prenom, estAbs from inscription with id_creneaux: {0}", activiteId);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception", e);
        }
        return registeredUsers;
    }

    private int getIdCompteByEmail(String email) {
        String query = "SELECT id_compte FROM compte WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_compte");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQL Exception", e);
        }
        throw new RuntimeException("User not found");
    }
}