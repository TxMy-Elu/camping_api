package sio.app.camping_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sio.app.camping_api.services.InscriptionService;

import java.util.List;
import java.util.Map;

@RestController
public class InscriptionController {

        private final InscriptionService inscriptionService;

    public InscriptionController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }
    public String processRequest(@Creneaux Creneaux creneaux) {
        Long id = creneaux.getId();
        Integer nb = creneaux.getNb();
        // Traitez les valeurs ici
        creneauxService.update(id , nb);
    }
    @GetMapping("/UpdateNbPlace")
        public List<Map<String, Object>> getAllSystemMetrics() {
            return inscriptionService.UpdateNbPlace();

        }
    }
