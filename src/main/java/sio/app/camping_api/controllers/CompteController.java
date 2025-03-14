package sio.app.camping_api.controllers;

import org.springframework.web.bind.annotation.*;
import sio.app.camping_api.services.CompteService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/compte")
@CrossOrigin("http://localhost:3000")
public class CompteController {

    private final CompteService CompteService;

    public CompteController(CompteService compteService) {
        this.CompteService = compteService;
    }

    @GetMapping("/allCompte")
    public List<Map<String, Object>> getAllSystemMetrics() {
        return CompteService.getAllCompte();

    }

    @GetMapping("/compteBloque")
    public List<Map<String, Object>> getCompteBloque() {
        return CompteService.getCompteBloque();

    }

    //renvoie notre role
    @PostMapping("/role")
    public Map<String, Object> getRole(@RequestBody Map<String, String> request) {
        String jwt = (String) request.get("jwt");
        return CompteService.getRole(jwt);
    }
}
