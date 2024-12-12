
package sio.app.camping_api.dto;

public class AbsenceRequest {
    private Long compteId;
    private boolean estAbsent;

    // Getters and setters
    public Long getCompteId() {
        return compteId;
    }

    public void setCompteId(Long compteId) {
        this.compteId = compteId;
    }

    public boolean isEstAbsent() {
        return estAbsent;
    }

    public void setEstAbsent(boolean estAbsent) {
        this.estAbsent = estAbsent;
    }
}