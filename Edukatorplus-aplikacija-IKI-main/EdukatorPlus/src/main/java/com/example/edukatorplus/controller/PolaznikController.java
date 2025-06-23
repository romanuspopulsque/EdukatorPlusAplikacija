package com.example.edukatorplus.controller;

import com.example.edukatorplus.DTO.PolaznikDTO;
import com.example.edukatorplus.service.PolaznikService;
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
}
