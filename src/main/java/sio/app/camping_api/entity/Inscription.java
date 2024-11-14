package sio.app.camping_api.entity;

import org.springframework.format.annotation.DateTimeFormat;

public class Inscription {
    private int id;
    private int id_compte;
    private int id_crenaux;
    private DateTimeFormat date_inscription;
    private int liste_attente;

    /**
     *
     * @param id
     * @param id_compte
     * @param id_crenaux
     * @param date_inscription
     * @param liste_attente
     */

    public Inscription(int id, int id_compte, int id_crenaux, DateTimeFormat date_inscription, int liste_attente) {
        this.id = id;
        this.id_compte = id_compte;
        this.id_crenaux = id_crenaux;
        this.date_inscription = date_inscription;
        this.liste_attente = liste_attente;
    }

    /**
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return id_compte
     */
    public int getId_compte() {
        return id_compte;
    }

    /**
     *
     * @param id_compte
     */
    public void setId_compte(int id_compte) {
        this.id_compte = id_compte;
    }

    /**
     *
     * @return id_crenaux
     */
    public int getId_crenaux() {
        return id_crenaux;
    }

    /**
     *
     * @param id_crenaux
     */
    public void setId_crenaux(int id_crenaux) {
        this.id_crenaux = id_crenaux;
    }

    /**
     *
     * @return date_inscription
     */
    public DateTimeFormat getDate_inscription() {
        return date_inscription;
    }

    /**
     *
     * @param date_inscription
     */
    public void setDate_inscription(DateTimeFormat date_inscription) {
        this.date_inscription = date_inscription;
    }

    /**
     *
     * @return liste_attente
     */
    public int getListe_attente() {
        return liste_attente;
    }

    /**
     *
     * @param liste_attente
     */
    public void setListe_attente(int liste_attente) {
        this.liste_attente = liste_attente;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "Inscription{" +
                "id=" + id +
                ", id_compte=" + id_compte +
                ", id_crenaux=" + id_crenaux +
                ", date_inscription=" + date_inscription +
                ", liste_attente=" + liste_attente +
                '}';
    }
}

