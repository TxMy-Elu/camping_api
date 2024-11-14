package sio.app.camping_api.entity;

public class Lieu {
    private int id_lieu;
    private int libelle;

    /**
     * @param id_lieu
     * @param libelle
     */
    public Lieu(int id_lieu, int libelle) {
        this.id_lieu = id_lieu;
        this.libelle = libelle;
    }

    /**
     * @return id_lieu
     */

    public int getId_lieu() {
        return id_lieu;
    }

    /**
     * @param id_lieu
     */

    public void setId_lieu(int id_lieu) {
        this.id_lieu = id_lieu;
    }

    /**
     * @return libelle
     */
    public int getLibelle() {
        return libelle;
    }

    /**
     * @param libelle
     */
    public void setLibelle(int libelle) {
        this.libelle = libelle;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Lieu{" +
                "id_lieu=" + id_lieu +
                ", libelle=" + libelle +
                '}';
    }
}
