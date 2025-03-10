package sio.app.camping_api.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sio.app.camping_api.services.CompteService;
import sio.app.camping_api.services.CreneauxService;

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
}
