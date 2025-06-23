package ffos.rsimunovic.edukatorplus.controller;

import ffos.rsimunovic.edukatorplus.service.DevDataService;
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

    @Operation(summary = "Generiraj prisustva za sve polaznike i radionice")
    @PostMapping("/prisustva")
    public String generirajPrisustva() {
        devDataService.generirajPrisustva();
        return "Uspješno generirana prisustva za sve polaznike i radionice.";
    }

    @Operation(summary = "Generiraj sve (polaznike, radionice, prisustva)")
    @PostMapping("/sve/{polaznici}/{radionice}")
    public String generirajSve(@PathVariable int polaznici,
                               @PathVariable int radionice) {
        devDataService.generirajPolaznike(polaznici);
        devDataService.generirajRadionice(radionice);
        devDataService.generirajPrisustva();
        return "Generirano: " + polaznici + " polaznika, " + radionice + " radionica, prisustva za sve.";
    }
}
