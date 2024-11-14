package sio.app.camping_api.entity;

public class Animation {
    private int id;
    private String nom;
    private String descriptif;

    /**
     * Constructeur de la classe Animation
     *
     * @param id
     * @param nom
     * @param descriptif
     */
    public Animation(int id, String nom, String descriptif) {
        this.id = id;
        this.nom = nom;
        this.descriptif = descriptif;
    }

    /**
     * Getter de l'id de l'animation
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter de l'id de l'animation
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter du nom de l'animation
     *
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter du nom de l'animation
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter du descriptif de l'animation
     *
     * @return descriptif
     */
    public String getDescriptif() {
        return descriptif;
    }

    /**
     * Setter du descriptif de l'animation
     *
     * @param descriptif
     */
    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    /**
     * Méthode toString de la classe Animation
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Animation{" + "id=" + id + ", nom='" + nom + '\'' + ", descriptif='" + descriptif + '\'' + '}';
    }
}
