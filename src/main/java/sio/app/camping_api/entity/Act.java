package sio.app.camping_api.entity;

public class Act {

    private int id_relation;
    private Compte compte;
    private Creneaux creneaux;

    /**
     * Constructor de la classe Act
     *
     * @param id_relation
     * @param compte
     * @param creneaux
     */
    public Act(int id_relation, Compte compte, Creneaux creneaux) {
        this.id_relation = id_relation;
        this.compte = compte;
        this.creneaux = creneaux;
    }

    /**
     * Getter de l'id de la relation
     *
     * @return id_relation
     */
    public int getId_relation() {
        return id_relation;
    }

    /**
     * Setter de l'id de la relation
     *
     * @param id_relation
     */
    public void setId_relation(int id_relation) {
        this.id_relation = id_relation;
    }

    /**
     * Getter du compte
     *
     * @return compte
     */
    public Compte getCompte() {
        return compte;
    }

    /**
     * Setter du compte
     *
     * @param compte
     */
    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    /**
     * Getter des créneaux
     *
     * @return creneaux
     */
    public Creneaux getCreneaux() {
        return creneaux;
    }

    /**
     * Setter des créneaux
     *
     * @param creneaux
     */
    public void setCreneaux(Creneaux creneaux) {
        this.creneaux = creneaux;
    }

    /**
     * Méthode toString de la classe Act
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Act{" + "id_relation=" + id_relation + ", compte=" + compte + ", creneaux=" + creneaux + '}';
    }
}
