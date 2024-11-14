package sio.app.camping_api.entity;


public class Compte {

    private int id_compte;

    private String username;
    private String email;
    private String password;
    private String role;
    private int abscences;

    /**
     * Constructeur de la classe Compte
     *
     * @param id_compte
     * @param username
     * @param email
     * @param password
     * @param role
     * @param abscences
     */
    public Compte(int id_compte, String username, String email, String password, String role, int abscences) {
        this.id_compte = id_compte;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.abscences = abscences;
    }

    /**
     * @return id_compte
     */
    public int getId_compte() {
        return id_compte;
    }

    /**
     * @param id_compte
     */
    public void setId_compte(int id_compte) {
        this.id_compte = id_compte;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return abscences
     */
    public int getAbscences() {
        return abscences;
    }

    /**
     * @param abscences
     */
    public void setAbscences(int abscences) {
        this.abscences = abscences;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "Compte{" + "id_compte=" + id_compte + ", username='" + username + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ", role='" + role + '\'' + ", abscences=" + abscences + '}';
    }


}
