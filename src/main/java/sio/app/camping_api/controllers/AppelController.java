package sio.app.camping_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sio.app.camping_api.services.AppelService;

import java.util.Map;

@RestController
@RequestMapping("/appel")
public class AppelController {

    @Autowired
    private AppelService appelService;

    @PutMapping("/gererAbsence")
    public void gererAbsence(@RequestBody Map<String, Object> request) {
        Long compteId = ((Number) request.get("compteId")).longValue();
        boolean estAbsent = (Integer) request.get("estAbsent") == 1;
        appelService.gererAbsence(compteId, estAbsent);
    }

     @PutMapping("/debloquerCompte")
    public void debloquerCompte(@RequestBody Map<String, Object> request) {
        Long compteId = ((Number) request.get("compteId")).longValue();
        appelService.debloquerCompte(compteId);
    }

}