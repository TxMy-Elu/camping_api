package sio.app.camping_api.entity;

import java.sql.Date;

public class Inscription {
    private int id;
    private int id_compte;
    private int id_crenaux;
    private Date date_inscription;
    private boolean liste_attente;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_compte() {
        return id_compte;
    }

    public void setId_compte(int id_compte) {
        this.id_compte = id_compte;
    }

    public int getId_crenaux() {
        return id_crenaux;
    }

    public void setId_crenaux(int id_crenaux) {
        this.id_crenaux = id_crenaux;
    }

    public Date getDate_inscription() {
        return date_inscription;
    }

    public void setDate_inscription(Date date_inscription) {
        this.date_inscription = date_inscription;
    }

    public boolean isListe_attente() {
        return liste_attente;
    }

    public void setListe_attente(boolean liste_attente) {
        this.liste_attente = liste_attente;
    }
}