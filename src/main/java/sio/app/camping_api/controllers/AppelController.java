package sio.app.camping_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sio.app.camping_api.dto.AbsenceRequest;
import sio.app.camping_api.services.AppelService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appel")
@CrossOrigin("http://localhost:3000")
public class AppelController {

    private final AppelService appelService;

    @Autowired
    public AppelController(AppelService appelService) {
        this.appelService = appelService;
    }

    @PutMapping("/gererAbsence")
    public void gererAbsence(@RequestBody List<AbsenceRequest> absences) {
        for (AbsenceRequest absence : absences) {
            appelService.gererAbsence(absence.getCompteId(), absence.isEstAbsent());
        }
    }

    @PutMapping("/debloquerCompte")
    public void debloquerCompte(@RequestBody Map<String, Object> request) {
        Long compteId = ((Number) request.get("compteId")).longValue();
        appelService.debloquerCompte(compteId);
    }
}