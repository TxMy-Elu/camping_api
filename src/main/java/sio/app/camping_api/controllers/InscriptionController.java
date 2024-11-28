package sio.app.camping_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sio.app.camping_api.dto.InscriptionRequest;
import sio.app.camping_api.services.InscriptionService;

@RestController
@RequestMapping("/inscription")
public class InscriptionController {

    @Autowired
    private InscriptionService inscriptionService;

    @PostMapping("/insertOrUpdateInscription")
    public void insertOrUpdateInscription(@RequestBody InscriptionRequest inscriptionRequest) {
        inscriptionService.insertInscription(
            inscriptionRequest.getInscription().getId_compte(),
            inscriptionRequest.getCreneaux().getId_creneaux(),
            inscriptionRequest.getInscription().getDate_inscription()
        );
    }
}