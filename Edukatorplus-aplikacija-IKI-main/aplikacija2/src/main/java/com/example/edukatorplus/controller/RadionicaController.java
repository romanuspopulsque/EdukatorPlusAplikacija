package com.example.edukatorplus.controller;

import com.example.edukatorplus.DTO.PolaznikDTO;
import com.example.edukatorplus.DTO.RadionicaDTO;
import com.example.edukatorplus.service.PrisustvoService;
import com.example.edukatorplus.service.RadionicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/radionice")
public class RadionicaController {

    @Autowired
    private RadionicaService radionicaService;

    @Autowired
    private PrisustvoService prisustvoService;

    @Operation(summary = "Dohvati sve radionice")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Uspješan dohvat"),
        @ApiResponse(responseCode = "500", description = "Interna greška servera")
    })
    @GetMapping
    public List<RadionicaDTO> getAll() {
        return radionicaService.getAll();
    }

    @Operation(summary = "Dohvati radionicu po ID-u")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Radionica pronađena"),
        @ApiResponse(responseCode = "404", description = "Radionica nije pronađena")
    })
    @GetMapping("/{id}")
    public RadionicaDTO getById(@PathVariable Long id) {
        return radionicaService.getById(id);
    }

    @Operation(summary = "Kreiraj novu radionicu")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Radionica kreirana"),
        @ApiResponse(responseCode = "400", description = "Neispravan zahtjev")
    })
    @PostMapping
    public RadionicaDTO create(@RequestBody RadionicaDTO dto) {
        return radionicaService.create(dto);
    }

    @Operation(summary = "Obriši radionicu po ID-u")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Uspješno obrisano"),
        @ApiResponse(responseCode = "404", description = "Radionica nije pronađena")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        radionicaService.delete(id);
    }

    @Operation(summary = "Dohvati sve polaznike na određenoj radionici")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Polaznici uspješno dohvaćeni"),
        @ApiResponse(responseCode = "404", description = "Radionica nije pronađena")
    })
    @GetMapping("/radionica/{radionicaId}/polaznici")
    public List<PolaznikDTO> getPolazniciNaRadionici(@PathVariable Long radionicaId) {
        return prisustvoService.getPolazniciByRadionicaId(radionicaId);
    }
    
    @Operation(summary = "Dohvati nadolazeće radionice")
@GetMapping("/nadolazece")
public List<RadionicaDTO> getNadolazeceRadionice() {
    return radionicaService.getNadolazece();
}

@Operation(summary = "Pretraži radionice po intervalu datuma")
@GetMapping("/pretraga")
public List<RadionicaDTO> pretraziPoDatumu(
        @RequestParam("od") String od,
        @RequestParam("do") String doDatuma) {

    LocalDate datumOd = LocalDate.parse(od);
    LocalDate datumDo = LocalDate.parse(doDatuma);
    return radionicaService.pretraziPoDatumu(datumOd, datumDo);
}


}
