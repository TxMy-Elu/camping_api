package sio.app.camping_api.entity;

import java.util.Calendar;

public class Creneaux {

    private int id_creneaux;
    private Calendar date_heure;
    private int id;
    private int duree;
    private int places_totales;
    private int places_prises;
    private int id_global;

    /***
     * Constructeur de la classe Creneaux
     * @param duree
     * @param date_heure
     * @param id
     * @param id_creneaux
     * @param places_totales
     * @param places_prises
     * @param id_global
     */

    public Creneaux(int id_creneaux, Calendar date_heure, int id, int duree, int places_totales, int places_prises,int id_global) {
        this.id_creneaux = id_creneaux;
        this.date_heure = date_heure;
        this.id = id;
        this.duree = duree;
        this.places_totales = places_totales;
        this.places_prises = places_prises;
        this.id_global = id_global;
    }

    /**
     *
     * @return id_global
     */
    public int getId_global() {
        return id_global;
    }

    /**
     *
     * @param id_global
     */
    public void setId_global(int id_global) {
        this.id_global = id_global;
    }

    /**
     * @return id_creneaux
     */
    public int getId_creneaux() {
        return id_creneaux;
    }

    /**
     * @param id_creneaux
     */
    public void setId_creneaux(int id_creneaux) {
        this.id_creneaux = id_creneaux;
    }

    /**
     * @return date_heure
     */
    public Calendar getDate_heure() {
        return date_heure;
    }

    /**
     * @param date_heure
     */
    public void setDate_heure(Calendar date_heure) {
        this.date_heure = date_heure;
    }

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return duree
     */
    public int getDuree() {
        return duree;
    }

    /**
     * @param duree
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

    /**
     * @return places_totales
     */
    public int getPlaces_totales() {
        return places_totales;
    }

    /**
     * @param places_totales
     */
    public void setPlaces_totales(int places_totales) {
        this.places_totales = places_totales;
    }

    /**
     * @return places_prises
     */
    public int getPlaces_prises() {
        return places_prises;
    }

    /**
     * @param places_prises
     */
    public void setPlaces_prises(int places_prises) {
        this.places_prises = places_prises;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "Creneaux{" + "id_creneaux=" + id_creneaux + ", date_heure=" + date_heure + ", id=" + id + ", duree=" + duree + ", places_totales=" + places_totales + ", places_prises=" + places_prises +", id_global=" + id_global + '}';
    }


}
