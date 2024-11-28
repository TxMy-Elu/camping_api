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

    @GetMapping("/UpdateNbPlace/{id_global}")
        public List<Map<String, Object>> getAllSystemMetrics(@PathVariable int id_global) {
            return inscriptionService.UpdateNbPlace(id_global);

        }
    }
