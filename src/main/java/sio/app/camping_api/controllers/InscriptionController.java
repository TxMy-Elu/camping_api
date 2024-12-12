package sio.app.camping_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sio.app.camping_api.dto.InscriptionRequest;
import sio.app.camping_api.services.InscriptionService;

import java.util.List;
import java.util.Map;

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

    @DeleteMapping("/deleteInscription")
    public void deleteInscription(@RequestBody InscriptionRequest inscriptionRequest) {
        inscriptionService.deleteInscription(
            inscriptionRequest.getInscription().getId_compte(),
            inscriptionRequest.getCreneaux().getId_creneaux()
        );
    }

    @GetMapping("/getRegisteredUsers/{activiteId}")
     public List<Map<String, Object>> getRegisteredUsers(@PathVariable Long activiteId) {
        return inscriptionService.getRegisteredUsers(activiteId);
    }
}

