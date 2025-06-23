package com.example.edukatorplus.controller;

import com.example.edukatorplus.service.DevDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dev")
@Tag(name = "DevDataController", description = "Rute za generiranje testnih podataka")
public class DevDataController {

    @Autowired
    private DevDataService devDataService;

    @Operation(summary = "Generiraj n polaznika")
    @PostMapping("/polaznici/{n}")
    public String generirajPolaznike(@PathVariable int n) {
        devDataService.generirajPolaznike(n);
        return "Uspješno generirano " + n + " polaznika.";
    }

    @Operation(summary = "Generiraj n radionica")
    @PostMapping("/radionice/{n}")
    public String generirajRadionice(@PathVariable int n) {
        devDataService.generirajRadionice(n);
        return "Uspješno generirano " + n + " radionica.";
    }

    @Operation(summary = "Generiraj n prisustava")
    @PostMapping("/prisustva/{n}")
    public String generirajPrisustva(@PathVariable int n) {
        devDataService.generirajPrisustva(n);
        return "Uspješno generirano " + n + " prisustava.";
    }

    @Operation(summary = "Generiraj sve (polaznike, radionice, prisustva)")
    @PostMapping("/sve/{polaznici}/{radionice}/{prisustva}")
    public String generirajSve(@PathVariable int polaznici,
                               @PathVariable int radionice,
                               @PathVariable int prisustva) {
        devDataService.generirajPolaznike(polaznici);
        devDataService.generirajRadionice(radionice);
        devDataService.generirajPrisustva(prisustva);
        return "Generirano: " + polaznici + " polaznika, " + radionice + " radionica, " + prisustva + " prisustava.";
    }
}
