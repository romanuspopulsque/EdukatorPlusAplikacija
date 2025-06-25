package ffos.rsimunovic.edukatorplus.controller;

import ffos.rsimunovic.edukatorplus.DTO.PolaznikDTO;
import ffos.rsimunovic.edukatorplus.DTO.PrisustvoDTO;
import ffos.rsimunovic.edukatorplus.service.PolaznikService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/polaznici")
public class PolaznikController {

    @Autowired
    private PolaznikService polaznikService;

    @Operation(summary = "Dohvati sve polaznike")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Uspješan dohvat"),
        @ApiResponse(responseCode = "500", description = "Interna greška servera")
    })
    @GetMapping
    public List<PolaznikDTO> getAll() {
        return polaznikService.getAll();
    }

    @Operation(summary = "Dohvati polaznika po ID-u")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Polaznik pronađen"),
        @ApiResponse(responseCode = "404", description = "Polaznik nije pronađen")
    })
    @GetMapping("/{id}")
    public PolaznikDTO getById(@PathVariable Long id) {
        return polaznikService.getById(id);
    }

    @Operation(summary = "Kreiraj novog polaznika")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Polaznik kreiran"),
        @ApiResponse(responseCode = "400", description = "Neispravan zahtjev")
    })
    @PostMapping
    public PolaznikDTO create(@RequestBody PolaznikDTO dto) {
        return polaznikService.create(dto);
    }

    @Operation(summary = "Obriši polaznika po ID-u")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Uspješno obrisano"),
        @ApiResponse(responseCode = "404", description = "Polaznik nije pronađen")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        polaznikService.delete(id);
    }
    
    @Operation(summary = "Pretraga polaznika po imenu i/ili prezimenu")
@GetMapping("/pretraga")
public List<PolaznikDTO> pretrazi(
        @RequestParam(required = false) String ime,
        @RequestParam(required = false) String prezime) {
    return polaznikService.pretraziPoImenuIPrezimenu(ime, prezime);
}

@Operation(summary = "Ažuriraj polaznika po ID-u")
@PutMapping("/{id}")
public PolaznikDTO update(@PathVariable Long id, @RequestBody PolaznikDTO dto) {
    return polaznikService.update(id, dto);
}

@Operation(summary = "Dohvati prisustva za polaznika")
@GetMapping("/{id}/prisustva")
public List<PrisustvoDTO> getPrisustvaZaPolaznika(@PathVariable Long id) {
    return polaznikService.getPrisustvaZaPolaznika(id);
}

@Operation(summary = "Pretraga polaznika po emailu")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Rezultati pronađeni"),
    @ApiResponse(responseCode = "400", description = "Neispravan upit")
})
@GetMapping("/pretraga/email")
public List<PolaznikDTO> pretraziPoEmailu(@RequestParam(required = false) String email) {
    return polaznikService.pretraziPoEmailu(email);
}




}
