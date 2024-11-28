package sio.app.camping_api.dto;

import sio.app.camping_api.entity.Creneaux;
import sio.app.camping_api.entity.Inscription;

public class InscriptionRequest {
    private Inscription inscription;
    private Creneaux creneaux;

    // Getters and setters
    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public Creneaux getCreneaux() {
        return creneaux;
    }

    public void setCreneaux(Creneaux creneaux) {
        this.creneaux = creneaux;
    }
}