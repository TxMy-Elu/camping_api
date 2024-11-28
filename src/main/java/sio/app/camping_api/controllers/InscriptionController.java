package sio.app.camping_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sio.app.camping_api.entity.Creneaux;
import sio.app.camping_api.entity.Inscription;
import sio.app.camping_api.services.InscriptionService;

@RestController
@RequestMapping("/inscription")
public class InscriptionController {

    @Autowired
    private InscriptionService inscriptionService;

    @PutMapping("/updateCreneaux")
    public void updateCreneaux(@RequestBody Creneaux request) {
        inscriptionService.updateCreneaux(request.getPlaces_prises(), request.getId_global());
    }

    @PostMapping("/insertInscription")
    public void insertInscription(@RequestBody Inscription request) {
        inscriptionService.insertInscription(request.getId_compte(), request.getId_crenaux(), request.getDate_inscription());
    }
}