package com.example.edukatorplus.controller;

import com.example.edukatorplus.DTO.PrisustvoDTO;
import com.example.edukatorplus.service.PrisustvoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prisustva")
public class PrisustvoController {

    @Autowired
    private PrisustvoService prisustvoService;

    @Operation(summary = "Dohvati sva prisustva")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Uspješan dohvat"),
        @ApiResponse(responseCode = "500", description = "Interna greška servera")
    })
    @GetMapping
    public List<PrisustvoDTO> getAll() {
        return prisustvoService.getAll();
    }

    @Operation(summary = "Evidentiraj prisustvo polaznika na radionici")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Prisustvo evidentirano"),
        @ApiResponse(responseCode = "400", description = "Neispravan zahtjev"),
        @ApiResponse(responseCode = "404", description = "Polaznik ili radionica nisu pronađeni")
    })
    @PostMapping
    public PrisustvoDTO create(@RequestBody PrisustvoDTO dto) {
        return prisustvoService.create(dto);
    }
    
@Operation(summary = "Evidentiraj prisustvo polaznika na radionici s parametrima")
@ApiResponses({
    @ApiResponse(responseCode = "201", description = "Prisustvo uspješno evidentirano"),
    @ApiResponse(responseCode = "400", description = "Neispravan zahtjev – nedostaju parametri ili nisu valjani"),
    @ApiResponse(responseCode = "404", description = "Radionica ili polaznik nisu pronađeni")
})
@PostMapping("/evidentiraj")
public PrisustvoDTO evidentirajPrisustvo(@RequestParam Long radionicaId,
                                         @RequestParam Long polaznikId,
                                         @RequestParam String status) {
    return prisustvoService.evidentirajPrisustvo(radionicaId, polaznikId, status);
}

@Operation(summary = "Dohvati prisustva za određenu radionicu")
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Uspješan dohvat prisustava"),
    @ApiResponse(responseCode = "404", description = "Radionica nije pronađena ili nema prisustava")
})
@GetMapping("/radionica/{radionicaId}")
public List<PrisustvoDTO> getPrisustvaByRadionica(@PathVariable Long radionicaId) {
    return prisustvoService.getByRadionicaId(radionicaId);
}


}
