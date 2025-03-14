package sio.app.camping_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sio.app.camping_api.dto.InscriptionRequest;
import sio.app.camping_api.services.InscriptionService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inscription")
@CrossOrigin("http://localhost:3000")
public class InscriptionController {

    @Autowired
    private InscriptionService inscriptionService;

    @PostMapping("/insertOrUpdateInscription")
    public void insertOrUpdateInscription(@RequestBody InscriptionRequest inscriptionRequest) {
        String jwt = inscriptionRequest.getJwt();
        inscriptionService.insertInscription(jwt, inscriptionRequest.getCreneaux().getId_creneaux(), inscriptionRequest.getInscription().getDate_inscription());
    }

  @DeleteMapping("/deleteInscription")
  public void deleteInscription(@RequestBody Map<String, Object> request) {
      String jwt = (String) request.get("jwt");
      Map<String, Object> creneaux = (Map<String, Object>) request.get("creneaux");
      int idCreneaux = (Integer) creneaux.get("id_creneaux");
      inscriptionService.deleteInscription(jwt, idCreneaux);
  }

    @GetMapping("/getRegisteredUsers/{activiteId}")
    public List<Map<String, Object>> getRegisteredUsers(@PathVariable Long activiteId) {
        return inscriptionService.getRegisteredUsers(activiteId);
    }

}

