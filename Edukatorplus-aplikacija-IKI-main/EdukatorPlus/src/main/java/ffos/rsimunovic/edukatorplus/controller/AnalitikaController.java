package ffos.rsimunovic.edukatorplus.controller;

import ffos.rsimunovic.edukatorplus.DTO.RadionicaDTO;
import ffos.rsimunovic.edukatorplus.DTO.PolaznikDTO;
import ffos.rsimunovic.edukatorplus.service.AnalitikaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analitika")
public class AnalitikaController {

    @Autowired
    private AnalitikaService analitikaService;

    @Operation(summary = "Dohvati radionice na kojima je polaznik sudjelovao")
    @GetMapping("/polaznik/{id}/radionice")
    public List<RadionicaDTO> getRadioniceByPolaznik(@PathVariable Long id) {
        return analitikaService.getRadioniceByPolaznik(id);
    }

    @Operation(summary = "Dohvati broj prisustava i odsustava po polazniku")
    @GetMapping("/polaznik/{id}/statistika")
    public Map<String, Long> getStatistikaPolaznika(@PathVariable Long id) {
        return analitikaService.getStatistikaPolaznika(id);
    }

    @Operation(summary = "Dohvati radionice bez polaznika")
    @GetMapping("/radionice/prazne")
    public List<RadionicaDTO> getRadioniceBezPolaznika() {
        return analitikaService.getRadioniceBezPolaznika();
    }

    @Operation(summary = "Dohvati polaznike bez prisustva")
    @GetMapping("/polaznici/bez-prisustva")
    public List<PolaznikDTO> getPolazniciBezPrisustva() {
        return analitikaService.getPolazniciBezPrisustva();
    }
}
