package ffos.rsimunovic.edukatorplus.controller;

import ffos.rsimunovic.edukatorplus.DTO.PrisustvoDTO;
import ffos.rsimunovic.edukatorplus.service.PrisustvoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<PrisustvoDTO>> getAll() {
        List<PrisustvoDTO> lista = prisustvoService.getAll();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Evidentiraj prisustvo polaznika na radionici")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Prisustvo evidentirano"),
        @ApiResponse(responseCode = "400", description = "Neispravan zahtjev"),
        @ApiResponse(responseCode = "404", description = "Polaznik ili radionica nisu pronađeni")
    })
    @PostMapping
    public ResponseEntity<PrisustvoDTO> create(@RequestBody PrisustvoDTO dto) {
        PrisustvoDTO rezultat = prisustvoService.create(dto);
        if (rezultat == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(rezultat);
    }

    @Operation(summary = "Evidentiraj prisustvo polaznika na radionici s parametrima")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Prisustvo uspješno evidentirano"),
        @ApiResponse(responseCode = "400", description = "Neispravan zahtjev – nedostaju parametri ili nisu valjani"),
        @ApiResponse(responseCode = "404", description = "Radionica ili polaznik nisu pronađeni")
    })
    @PostMapping("/evidentiraj")
    public ResponseEntity<PrisustvoDTO> evidentirajPrisustvo(@RequestParam Long radionicaId,
                                                             @RequestParam Long polaznikId,
                                                             @RequestParam String status) {
        if (radionicaId == null || polaznikId == null || status == null || status.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        PrisustvoDTO rezultat = prisustvoService.evidentirajPrisustvo(radionicaId, polaznikId, status);
        if (rezultat == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(rezultat);
    }

    @Operation(summary = "Dohvati prisustva za određenu radionicu")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Uspješan dohvat prisustava"),
        @ApiResponse(responseCode = "404", description = "Radionica nije pronađena ili nema prisustava")
    })
    @GetMapping("/radionica/{radionicaId}")
    public ResponseEntity<List<PrisustvoDTO>> getPrisustvaByRadionica(@PathVariable Long radionicaId) {
        List<PrisustvoDTO> prisustva = prisustvoService.getByRadionicaId(radionicaId);
        if (prisustva == null || prisustva.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(prisustva);
    }
}
