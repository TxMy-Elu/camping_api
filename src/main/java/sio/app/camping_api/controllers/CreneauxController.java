package sio.app.camping_api.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sio.app.camping_api.services.CreneauxService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/creneaux")
@CrossOrigin("http://localhost:3000")
public class CreneauxController {

    private final CreneauxService  creneauxService;

    public CreneauxController(CreneauxService creneauxService) {
        this.creneauxService = creneauxService;
    }

    @GetMapping("/allCreneaux")
    public List<Map<String, Object>> getAllSystemMetrics() {
        return creneauxService.getAllCreneaux();

    }
}
